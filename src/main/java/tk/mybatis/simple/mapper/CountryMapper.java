package tk.mybatis.simple.mapper;

import java.util.List;
import java.util.Map;

import tk.mybatis.simple.model.Country;

public interface CountryMapper {
	/**
	 * 查询所有国家信息
	 * @return
	 */
	List<Country> selectAll();
	
	/**
	 * 调用存储过程查询国家信息
	 * @param params
	 * @return
	 */
	Object selectCountries(Map<String,Object> params);
}
