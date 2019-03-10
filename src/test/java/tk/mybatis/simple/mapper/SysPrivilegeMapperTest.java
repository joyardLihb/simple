package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;

public class SysPrivilegeMapperTest extends BaseMapperTest{
	@Test
	public void testSelectAll() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			SysPrivilegeMapper sysPrivilegeMapper = sqlSession.getMapper(SysPrivilegeMapper.class);
			List<SysPrivilege> sysPrivilegeList = sysPrivilegeMapper.selectAll();
			Assert.assertNotNull(sysPrivilegeList);//判定userList不为空
			Assert.assertTrue(sysPrivilegeList.size() == 5);//判定userList长度为2
		} finally {
			sqlSession.close();
		}
	}
	@Test
	public void insert() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			SysPrivilegeMapper sysPrivilegeMapper = sqlSession.getMapper(SysPrivilegeMapper.class);
			SysPrivilege privilege = new SysPrivilege();
			privilege.setPrivilegeName("测试模块");
			privilege.setPrivilegeUrl("dddd");
			//插入数据
			int result = sysPrivilegeMapper.insert(privilege);
			Assert.assertTrue(result==1);//插入一条数据
//			Assert.assertNull(privilege.getId());
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
	/**--------------------------测试一对多映射嵌套查询开始-------------------------------**/
	@Test
	public void selectAllRoleAndPrivilege() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> roleList = roleMapper.selectAllRoleAndPrivilege();
			Assert.assertNotNull(roleList);//判定userList不为空
			Assert.assertEquals(2, roleList.size());//判定userList的长度为2
			System.out.println("总共角色数：" + roleList.size());
			for(SysRole role : roleList){
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
	public void selectById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(1L);
			Assert.assertNotNull(role);//判定userList不为空
			System.out.println("角色名：：" + role.getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	/**--------------------------测试一对一映射嵌套查询结束-------------------------------**/
}
