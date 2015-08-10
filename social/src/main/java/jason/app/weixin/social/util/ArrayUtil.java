package jason.app.weixin.social.util;

public class ArrayUtil {

	public static String[] toArray(String hobbys) {
		// TODO Auto-generated method stub
		if(hobbys==null) return null;
		String text = hobbys.substring(1,hobbys.length()-1);
		return text.split(",");
	}

	public static Long[] toLongArray(String hobbys) {
		// TODO Auto-generated method stub
		if(hobbys==null || hobbys=="null") return null;
		String text = hobbys.substring(1,hobbys.length()-1);
		String[] array =  text.split(",");
		Long[] result = new Long[array.length];
		for(int i=0;i<array.length;i++) {
			result[i]= Long.parseLong(array[i].trim());
		}
		return result;
	}

}
