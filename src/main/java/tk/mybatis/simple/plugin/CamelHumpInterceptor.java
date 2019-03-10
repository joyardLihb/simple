package tk.mybatis.simple.plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import java.sql.Statement;
/**
 * mybatis Map类型下划线转驼峰形式
 * @author Administrator
 *
 */
@Intercepts(
		@Signature(type=ResultSetHandler.class,
		           method = "handleResultSets",
		           args={Statement.class}))
public class CamelHumpInterceptor implements Interceptor {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		//先执行得到结果，再对结果进行处理
		List<Object> list = (List<Object>)invocation.proceed();
		for(Object object : list){
			//如果结果是Map型，就对Map的key进行转换
			if(object instanceof Map){
				processMap((Map)object);
			}
		}
		return list;
	}
	/**
	 * 处理map结果中的key
	 * @param map
	 */
	private static void processMap(Map<String,Object> map){
		Set<String> hashSet = new HashSet<String>(map.keySet());//此处一定要新建一个hashSet对象，方便遍历和remove
		for(String key : hashSet){
			//将大写字母开头的key转换为小写字母，同时带有下划线的变为驼峰
			if((key.charAt(0)>= 'A' && key.charAt(0) <= 'Z') || key.indexOf("_")>=0){
				Object value = map.get(key);
				map.remove(key);
				map.put(underlineToCamelHump(key), value);
			}
		}
	}
	/**
	 * 小写驼峰转换
	 * @param inputString
	 * @return
	 */
	private static String underlineToCamelHump(String inputString){
		StringBuffer sb = new StringBuffer();
		boolean nextUpperCase = false;
		for(int i=0;i<inputString.length();i++){
			char c = inputString.charAt(i);
			if('_' == c){ //如果字符c == '_’，则下一个字母大写
				nextUpperCase = true;
			}
			else{
				if(nextUpperCase){
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				}
				else{
					sb.append(Character.toLowerCase(c));
				}
			}
		}
		return sb.toString();
	}
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
