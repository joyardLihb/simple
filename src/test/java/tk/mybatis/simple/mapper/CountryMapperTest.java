package tk.mybatis.simple.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.Country;

public class CountryMapperTest extends BaseMapperTest{
	@Test
	public void testSelectAll() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			List<Country> countryList = sqlSession.selectList("tk.mybatis.simple.mapper.CountryMapper.selectAll");//通过sqlSession的selectList方法查找到CountryMapper.xml中id为selectAll的方法
			printCountryList(countryList);																		  //此时务必带上xml的namespace，因为selectAll已经不唯一，在userMapper.xml中也定义了一个同名函数
		} finally {
			sqlSession.close();
		}
	}
	
	private void printCountryList(List<Country> countryList){
		for(Country country : countryList){
			System.out.printf("%-4d%4s%4s\n",country.getId(),country.getCountryname(),country.getCountrycode());
		}
	}
	/**
	 * 调用oracle存储过程，返回两个游标结果集
	 */
	@Test
	public void selectCountries() {
		SqlSession sqlSession = super.getSqlSession();//获取一个sqlSession
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			Object retObj = countryMapper.selectCountries(params);
			List<Country> list1 = (List<Country>)params.get("list1");
			List<Country> list2 = (List<Country>)params.get("list2");
			Assert.assertNotNull(list1);
			Assert.assertNotNull(list2);
			for(Country country : list1){
				System.out.println("国家名称：" + country.getCountryname());
			}
			for(Country country : list2){
				System.out.println("国家名称：" + country.getCountryname());
			}
		} finally {
			sqlSession.close();
		}
	}
}
