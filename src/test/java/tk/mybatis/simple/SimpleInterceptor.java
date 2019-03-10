package tk.mybatis.simple;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 简单拦截器示例
 * @author Administrator
 *
 */
@Intercepts(
		@Signature(type=Executor.class,
		           method = "query",
		           args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class}))
public class SimpleInterceptor implements Interceptor {
	private String name;
	
	public SimpleInterceptor(String name){
		this.name = name;
	}
	
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("进入拦截器:" + name);
		Object object = invocation.proceed();
		System.out.println("跑出拦截器:" + name);
		return object;
	}
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}
}
