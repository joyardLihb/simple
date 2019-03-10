package tk.mybatis.simple.mapper;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

public class InterceptTest extends BaseMapperTest{
	@Test
	public void testIntercept() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String,Object> userMap = userMapper.selectUserMapInfoById(1l);
			Assert.assertEquals("admin", userMap.get("userName"));
			System.out.println(userMap);
		} finally {
			sqlSession.close();
		}
	}
}
