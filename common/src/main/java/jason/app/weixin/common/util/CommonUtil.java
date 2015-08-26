package jason.app.weixin.common.util;

public class CommonUtil {
	public static String getHeOrShe(Integer sex) {
		if(sex!=null) {
			if(1==sex) {
				return "他";
			}else {
				return "她";
			}
		}
		return "他";
	}
}
