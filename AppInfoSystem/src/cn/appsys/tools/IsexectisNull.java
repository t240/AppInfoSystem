package cn.appsys.tools;
/**
 * �ǿ��жϹ�����
 * @author DELL
 *
 */
public class IsexectisNull {
	//�ǿ��ж�
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
