package tk.mybatis.simple.mapper;


import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysRole;

public class RoleMapperTest extends BaseMapperTest{
	@Test
	public void updateById() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setEnabled(1);
			//更新数据
			int result = roleMapper.updateByPrimaryKey(role);
			Assert.assertTrue(result==1);//插入一条数据
		} finally {
			sqlSession.commit();//尝试一次手工提交事务，检测是否持久化到数据库中
			sqlSession.close();
		}
	}
}
