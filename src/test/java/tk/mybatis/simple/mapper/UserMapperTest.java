package tk.mybatis.simple.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.pojo.SysRoleExtend;

public class UserMapperTest extends BaseMapperTest{
	@Test
	public void testSelectAll() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAll();
			Assert.assertNotNull(userList);//判定userList不为空
			Assert.assertTrue(userList.size() == 2);//判定userList长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void testSelectById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1001l);
			Assert.assertNotNull(user);//判定user不为空
			Assert.assertEquals("test", user.getUserName());//判定user的用户名为test
		} finally {
			sqlSession.close();
		}
	}
	/**--------------------------测试一对一映射嵌套查询开始-------------------------------
	@Test
	public void selectUserAndRoleById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001l);
			Assert.assertNotNull(user);//判定user不为空
			Assert.assertEquals("test", user.getUserName());//判定user的用户名为test
			Assert.assertEquals("普通用户", user.getSysRole().getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectUserAndRoleById2() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById2(1001l);
			Assert.assertNotNull(user);//判定user不为空
			Assert.assertEquals("test", user.getUserName());//判定user的用户名为test
			Assert.assertEquals("普通用户", user.getSysRole().getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectUserAndRoleByIdSelect() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001l);
			Assert.assertNotNull(user);//判定user不为空
			Assert.assertEquals("test", user.getUserName());//判定user的用户名为test
			Assert.assertEquals("普通用户", user.getSysRole().getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectUserAndRoleByIdSelectLazy() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001l);
			System.out.println("此时并未执行查询role信息的SQL.");
//			user.equals(null);//由于mybatis的lazyLoadTriggerMethods参数中定义的默认：equals，clone,hashcode,toString四个方法一旦执行，mybatis会加载全部数据
			Assert.assertNotNull(user);//判定user不为空
			Assert.assertEquals("test", user.getUserName());//判定user的用户名为test
			System.out.println("此时执行user.getSysRole().getRoleName()查询role信息.");
			Assert.assertEquals("普通用户", user.getSysRole().getRoleName());
			System.out.println("此时打印执行查询role信息的SQL.");
		} finally {
			sqlSession.close();
		}
	}
	--------------------------测试一对一映射嵌套查询结束-------------------------------**/
	/**--------------------------测试一对多映射嵌套查询开始-------------------------------**/
	@Test
	public void selectAllUserAndRole() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRole();
			Assert.assertNotNull(userList);//判定userList不为空
			Assert.assertEquals(2, userList.size());//判定userList的长度为2
			System.out.println("总共用户数：" + userList.size());
			for(SysUser user : userList){
				System.out.println("用户名：" + user.getUserName());
				for(SysRole role : user.getRoleList()){
					System.out.println("角色名：：" + role.getRoleName());
					for(SysPrivilege privilege : role.getSysPrivilegeList()){
						System.out.println("权限名：：" + privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectAllRolePrivilegesById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllRolePrivilegesById(1L);
			Assert.assertNotNull(user);//判定userList不为空
			System.out.println("用户名：" + user.getUserName());
			for(SysRole role : user.getRoleList()){
				System.out.println("角色名：：" + role.getRoleName());
				for(SysPrivilege privilege : role.getSysPrivilegeList()){
					System.out.println("权限名：：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectRolesByUserIdRoleMapper() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> userRoles = roleMapper.selectRolesByUserId(1L);
			Assert.assertNotNull(userRoles);//判定userRoles不为空
			Assert.assertEquals(userRoles.size(), 2);//判定userRoles长度为2
			for(SysRole role : userRoles){
				System.out.println("角色名：：" + role.getRoleName());
				for(SysPrivilege privilege : role.getSysPrivilegeList()){
					System.out.println("权限名：：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	/**--------------------------测试一对多映射嵌套查询结束-------------------------------**/
	@Test
	public void testSelectByUser() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test");
			List<SysUser> retUsers = userMapper.selectByUser(user);
			Assert.assertNotNull(retUsers);//判定retUsers不为空
			Assert.assertEquals(1, retUsers.size());//判定retUsers长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectByIdList() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> retUsers = userMapper.selectByIdList(idList);
			Assert.assertNotNull(retUsers);//判定retUsers不为空
			Assert.assertEquals(2, retUsers.size());//判定retUsers长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectByIdOrUserName() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
//			user.setUserName("test");
			user.setId(1001L);
			SysUser retUser = userMapper.selectByIdOrUserName(user);
			Assert.assertNotNull(retUser);//判定retUsers不为空
			Assert.assertEquals("test", retUser.getUserName());//判定retUsers长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectRolesByUserId() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> userRoles = userMapper.selectRolesByUserId(1L);
			Assert.assertNotNull(userRoles);//判定userRoles不为空
			Assert.assertEquals(userRoles.size(), 2);//判定userRoles长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectRoleExtendsByUserId() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRoleExtend> userRoleExtends = userMapper.selectRoleExtendsByUserId(1L);
			Assert.assertNotNull(userRoleExtends);//判定userRoles不为空
			Assert.assertEquals("admin",userRoleExtends.get(0).getUserName());//判定当前角色的用户名为admin
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectRoleAndUsersByUserId() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> userRoles = userMapper.selectRoleAndUsersByUserId(1L);
			Assert.assertNotNull(userRoles);//判定userRoles不为空
			Assert.assertEquals("admin",userRoles.get(0).getUser().getUserName());//判定当前角色的用户名为admin
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void insert() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test1@mybatis.tk");
			user.setUserInfo("test1 infor");
			user.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			user.setCreateTime(new Date());
			//插入数据
			int result = userMapper.insert(user);
			Assert.assertTrue(result==1);//插入一条数据
			Assert.assertNull(user.getId());//没有给id赋值，并且也没有配置回写给id赋值
		} finally {
//			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.rollback();//为了不影响测试，这里选择回滚事务，当然sqlSession.getSqlSession()需要手动commit，不手动也不会提交数据库，也可以不用此语句回滚
			sqlSession.close();
		}
	}
	@Test
	public void insertList() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> sysUserList = new ArrayList<SysUser>();
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test1@mybatis.tk");
			user.setUserInfo("test1 infor");
			user.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			user.setCreateTime(new Date());
			sysUserList.add(user);
			SysUser user2 = new SysUser();
			user2.setUserName("test2");
			user2.setUserPassword("123456");
			user2.setUserEmail("test1@mybatis.tk");
			user2.setUserInfo("test1 infor");
			user2.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			user2.setCreateTime(new Date());
			sysUserList.add(user2);
			//插入数据
			int result = userMapper.insertList(sysUserList);
			Assert.assertTrue(result==2);//插入一条数据
			Assert.assertNull(user.getId());//没有给id赋值，并且也没有配置回写给id赋值
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
//			sqlSession.rollback();//为了不影响测试，这里选择回滚事务，当然sqlSession.getSqlSession()需要手动commit，不手动也不会提交数据库，也可以不用此语句回滚
			sqlSession.close();
		}
	}
	@Test
	public void insert2() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("123456");
//			user.setUserEmail("test2@mybatis.tk");
			user.setUserInfo("测试用户2");
			user.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			user.setCreateTime(new Date());
			//插入数据
			int result = userMapper.insert2(user);
			System.out.println(user.getId());
			Assert.assertTrue(result==1);//插入一条数据
			Assert.assertNotNull(user.getId());//配置回写给id赋值
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void insert3() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("123456");
			user.setUserEmail("test2@mybatis.tk");
			user.setUserInfo("测试用户2");
			user.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			user.setCreateTime(new Date());
			//插入数据
			int result = userMapper.insert3(user);
			System.out.println(user.getId());
			Assert.assertTrue(result==1);//插入一条数据
			Assert.assertNotNull(user.getId());//配置回写给id赋值
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void updateById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1001L);
			user.setUserPassword("12345678");
			//更新数据
			int result = userMapper.updateById(user);
			Assert.assertTrue(result==1);//插入一条数据
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void updateBySelective() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1010L);
			user.setUserName("test1");
			user.setUserPassword("12345678");
			//更新数据
			int result = userMapper.updateBySelective(user);
//			int result = userMapper.updateById(user);如果此时调用此函数，会把本来有的属性更新为null,而调用上边的有选择更新，则不会
			Assert.assertTrue(result==1);//插入一条数据
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void updateByMap() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user_name", "test");
			map.put("user_password", "111111");
			map.put("id", 1001L);
			//更新数据
			int result = userMapper.updateByMap(map);
			Assert.assertTrue(result==1);//插入一条数据
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void deleteById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//更新数据
			int result = userMapper.deleteById(1010L);
			Assert.assertTrue(result==1);//插入一条数据
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	@Test
	public void selectRolesByUserIdAndEnabled() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//			List<SysRole> userRoles = userMapper.selectRolesByUserIdAndEnabled(1L,1);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", 1L);
			param.put("enabled", 1);
			List<SysRole> userRoles = userMapper.selectRolesByUserIdAndEnabled(param);
			Assert.assertNotNull(userRoles);//判定userRoles不为空
			Assert.assertEquals(2,userRoles.size());//判定当前用户拥有两个角色
		} finally {
			sqlSession.close();
		}
	}
	/**测试存储过程调用**/
	@Test
	public void selectUserById(){
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserById(user);
			Assert.assertEquals("admin", user.getUserName());//判定user的用户名为admin
			System.out.println("用户名:"+user.getUserName());
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void selectUserPage(){
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userName", "test");
			params.put("offset", 0);//pageNum
			params.put("limit", 10);//pageSize
			List<SysUser> userList = userMapper.selectUserPage(params);
			Assert.assertTrue(userList.size()>0);
			Assert.assertTrue(params.containsKey("total"));
			System.out.println("返回总数：" + params.get("total"));
			for(SysUser user : userList){
				System.out.println("用户名：" + user.getUserName());
			}
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void insertUserAndRoles(){
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("123456");
			user.setUserEmail("test2@mybatis.tk");
			user.setUserInfo("测试用户2");
			user.setHeadImg(new byte[]{1,2,3});//正常情况应该是一个图片的二进制流
			userMapper.insertUserAndRoles(user, "1,2");
			Assert.assertNotNull(user.getId());
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	@Test
	public void deleteUserById(){
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			userMapper.deleteUserById(1007L);
		} finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
}
