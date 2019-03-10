package tk.mybatis.simple.mapper;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

public class BaseMapperTest {
	private static SqlSessionFactory sqlSessionFactory;
	@BeforeClass
	public static void init(){
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");//通过Resources将mybatis配置文件读入reader
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);//使用reader创建工厂对象，加载所有的mappers中配置文件，
																			 //然后解析每一个xml文件，得到所有的属性配置和执行SQL信息供后续sqlSession使用
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public SqlSession getSqlSession(){
		return  sqlSessionFactory.openSession();
	}
}
