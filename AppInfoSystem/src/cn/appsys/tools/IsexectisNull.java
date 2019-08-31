package cn.appsys.tools;
/**
 * 非空判断工具类
 * @author DELL
 *
 */
public class IsexectisNull {
	//非空判断
	public static boolean hasLength(String str) {
		return null != str && !"".equals(str.trim());
	}

	public static boolean isBlank(String str) {
		return hasLength(str);
	}
	
	public static boolean isnull(Object obj) {
		return null != obj;
	}
}
