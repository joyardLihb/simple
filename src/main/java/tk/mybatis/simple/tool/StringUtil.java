package tk.mybatis.simple.tool;

public class StringUtil {
	public static boolean isEmpty(String str){
		return str == null || "".equals(str);
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static void print(Object parameter){
		System.out.println(parameter);
	}
}
