package jason.app.weixin.social.util;

public class ArrayUtil {

	public static String[] toArray(String hobbys) {
		// TODO Auto-generated method stub
		if(hobbys==null) return null;
		String text = hobbys.substring(1,hobbys.length()-1);
		return text.split(",");
	}

}
