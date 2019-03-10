package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class MybatisCacheTest extends BaseMapperTest{
	@Test
	public void testL1Cache() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		SysUser user1 = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user1 = userMapper.selectById(1l);
			Assert.assertEquals("admin", user1.getUserName());
			user1.setUserName("New Name");
			SysUser user2 = userMapper.selectById(1l);
			//虽然上边的user1.setUserName("New Name")没有更新到数据库，但由于两次查询过程中没有发生其他数据库操作，mybatis一级缓存
			//会缓存第一次查询出的对象，直接返回，所以user1和user2的用户名仍然相等。可以在方法里使用flushCache="true",主动清空一级缓存。但这样做会增加系统数据库查询次数
			//导致性能下降
			Assert.assertEquals(user1.getUserName(), user2.getUserName());
			//user1和user2是同一个实例
			Assert.assertEquals(user1, user2);
		} finally {
			sqlSession.close();
		}
		System.out.println("开启新的SqlSession!");
		sqlSession = super.getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user2 = userMapper.selectById(1l);
			//新session中获取的user2名字并不等于"New Name"，因为一级缓存只存在在当前sqlSession中
			Assert.assertEquals("admin", user2.getUserName());
			Assert.assertNotEquals("New Name", user2.getUserName());
			//user1和user2是并不是同一个实例
			Assert.assertNotEquals(user1, user2);
			userMapper.deleteById(2L);//执行了update,delete,insert操作后，mybatis都会清空一级缓存，重新获取的user将是新的对象
			SysUser user3 = userMapper.selectById(1l);
			Assert.assertNotEquals(user2, user3);
		} finally {
			sqlSession.close();
		}
	}
	/**
	 * 测试默认的mybatis二级缓存，基于MAP的二级缓存
	 */
	@Test
	public void testL2Cache() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		SysRole role1 = null;
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			role1 = roleMapper.selectById(1l);//第一次查询：二级缓存未命中，一级缓存也没有命中
			//需要注意使用redis缓存时，第二次执行会报错，因为就算关闭连接后，redis缓存会将之前的结果缓存保存到redis服务器。第二次测试时，将直接命中redis缓存，
			//由于内容中名字为第一次缓存的New Name，这里一定会报错，且后续的判断Assert.assertEquals(role1, role2);也会报错
			Assert.assertEquals("管理员", role1.getRoleName());
			role1.setRoleName("New Name");//需要注意更新对象会清空一二级缓存
			SysRole role2 = roleMapper.selectById(1l);//第二次查询：二级缓存未命中，一级缓存命中
			//虽然上边的user1.setUserName("New Name")没有更新到数据库，但由于两次查询过程中没有发生其他数据库操作，mybatis一级缓存
			//会缓存第一次查询出的对象，直接返回，所以user1和user2的用户名仍然相等。可以在方法里使用flushCache="true",主动清空一级缓存。但这样做会增加系统数据库查询次数
			//导致性能下降
			Assert.assertEquals(role1.getRoleName(), role2.getRoleName());
			//user1和user2是同一个实例
			Assert.assertEquals(role1, role2);
//			roleMapper.updateByPrimaryKey(role1);//此处会清空一二级缓存，导致第三、四次无法命中
		} finally {
			sqlSession.close();//session关闭后，查询结果缓存到二级缓存中，后续新开session即可使用
		}
		System.out.println("开启新的SqlSession!");
		sqlSession = super.getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role2 = roleMapper.selectById(1l);//第三次查询：二级缓存命中，一级缓存未命中
			//新session中获取的role2名字也等于"New Name"，因为二级缓存存在在当前sqlSessionFactory中，一般一个服务就一个sqlSessionFactory
			Assert.assertEquals("New Name", role2.getRoleName());
			//role1和role2是并不是同一个实例
			Assert.assertNotEquals(role1, role2);
			SysRole role3 = roleMapper.selectById(1l);//第四次查询：二级缓存命中，一级缓存未命中
			//role2和role3是并不是同一个实例，因为每次都是缓存中反序列化出来，保证了实例之间互不影响
			Assert.assertNotEquals(role2, role3);
		} finally {
			sqlSession.close();
		}
	}
	/**
	 * 测试二级缓存脏数据的产生：由于按照命名空间定义的二级缓存，如果查询和更新不在同一个命名空间中，会导致更新的数据不能同步到查询的缓存，造成脏数据
	 * 解决方案：保证更新操作务必和查询操作在同一个命名空间中:<cache-ref namespace="tk.mybatis.simple.mapper.RoleMapper"/>
	 */
	@Test
	public void testL2CacheDirtyData() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRoleAndUsersByUserId(1001l); //第一个session结束后，二级缓存会将查询结果缓存到uerMapper命名空间对应的缓存中
			for(SysRole role : roleList){
				System.out.println("原来的createBy:" + role.getCreateBy());
			}
		} finally {
			sqlSession.close();
		}
		//打开一个新的session，修改角色名称
	    sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setCreateBy(3L);
			roleMapper.updateByPrimaryKey(role);//第二个session更新数据时会清空一二级缓存，故RoleMapper命名空间的二级缓存会清空
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
		//再次打开新的session，重新读取user的角色名
		sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRoleAndUsersByUserId(1001l);//第三个session，会命中命名空间UserMapper缓存的数据，此时缓存数据和数据库中已经不一致
			for(SysRole role : roleList){
				System.out.println("现在的createBy:" + role.getCreateBy());
			}
		} finally {
			sqlSession.close();
		}
	}
}
