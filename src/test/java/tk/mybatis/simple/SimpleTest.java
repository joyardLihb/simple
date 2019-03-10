package tk.mybatis.simple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;

import tk.mybatis.simple.mapper.CountryMapper;
import tk.mybatis.simple.model.Country;

public class SimpleTest{
	@Test
	public void test() throws SQLException {
		/**第一部分：指定日志和配置对象**/
		//指定使用log4j记录日志
		LogFactory.useLog4JLogging();
		//创建配置对象
		final Configuration config = new Configuration();
		//配置setting中部分属性
		config.setCacheEnabled(true);
		config.setLazyLoadingEnabled(false);
		config.setAggressiveLazyLoading(true);
		
		/**第二部分：添加拦截器**/
		SimpleInterceptor simpleInterceptor1 = new SimpleInterceptor("拦截器 1");
		SimpleInterceptor simpleInterceptor2 = new SimpleInterceptor("拦截器 2");
		config.addInterceptor(simpleInterceptor1);
		config.addInterceptor(simpleInterceptor2);
		
		/**第三部分:创建数据源及JDBC事务**/
		UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
		unpooledDataSource.setDriver("com.mysql.jdbc.Driver");
		unpooledDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
		unpooledDataSource.setUsername("root");
		unpooledDataSource.setPassword("123456");
		
		Transaction transaction = new JdbcTransaction(unpooledDataSource, null, false);
		
		/**第四部分:创建Executor**/
		final Executor executor = config.newExecutor(transaction);
		
		/**第五部分:创建SqlSource对象**/
		StaticSqlSource staticSqlSource = new StaticSqlSource(config, "select * from country where id = ?");
		
		/**第六部分:创建参数映射配置**/
		TypeHandlerRegistry registry = new TypeHandlerRegistry();
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		parameterMappings.add(new ParameterMapping.Builder(config, "id", registry.getTypeHandler(Long.class)).build());
		ParameterMap.Builder parameterBuilder = new ParameterMap.Builder(config, "id", Country.class, parameterMappings);
		
		/**第七部分:创建结果映射配置，构建者模式**/
		List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
		resultMappings.add(new ResultMapping.Builder(config, "id","id",Long.class).build());
		resultMappings.add(new ResultMapping.Builder(config, "countryname","countryname",String.class).build());
		resultMappings.add(new ResultMapping.Builder(config, "countrycode","countrycode",String.class).build());
		ResultMap resultMap = new ResultMap.Builder(config, "defaultResultMap", Country.class,resultMappings).build();
		
		/**第八部分:创建缓存对象，装饰模式**/
		final Cache countryCache = 
			new SynchronizedCache(		//同步缓存
					new SerializedCache(		//序列化缓存
							new LoggingCache(		//日志缓存
									new LruCache(		//最少使用缓存
											new PerpetualCache("country_cache")))));//持久缓存
		
		
		/**第九部分:创建MappedStatement对象，构建者模式，组合之前各个部分准备好的对象**/
		MappedStatement.Builder msBuilder = new MappedStatement.Builder(
				config,
				"tk.mybatis.simple.mapper.CountryMapper.selectById",
				staticSqlSource,
				SqlCommandType.SELECT);
		msBuilder.parameterMap(parameterBuilder.build());
		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		resultMaps.add(resultMap);
		//设置返回值
		msBuilder.resultMaps(resultMaps);
		//设置缓存
		msBuilder.cache(countryCache);
		//创建ms
		MappedStatement ms = msBuilder.build();
		/**第十部分:最终通过MappedStatement调用接口，查询出结果**/
		//1、使用这种方法已经可以查询到结果，但有更高一层的办法查询
//		List<Country> countries = executor.query(ms, 1L, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
		config.addMappedStatement(ms);
		SqlSession sqlSession = new DefaultSqlSession(config, executor, false);
		//2、更高的方法查询，即我们在测试代码中调用的方式，也是早期mybatis使用的方式，此方式实际上不需要mapper.xml,也不需要mapper接口
//		Country country = sqlSession.selectOne("tk.mybatis.simple.mapper.CountryMapper.selectById",2L);
//		System.out.println(country);
		//3、目前最终的查询方式，建立mapper接口，动态代理生产接口实现类，服务层直接调用接口查询
		MapperProxyFactory<CountryMapper> mapperProxyFactory = new MapperProxyFactory<CountryMapper>(CountryMapper.class);
		CountryMapper countryMapper = mapperProxyFactory.newInstance(sqlSession);
		Country country = countryMapper.selectById(2L);
		System.out.println(country);
	}
}
