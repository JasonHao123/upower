package jason.app.weixin.common;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.tika.Tika;

public class FileUtilsTest {

	public static void main2(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL website = new URL("https://s3.cn-north-1.amazonaws.com.cn/weaktie/weak-ties2.jpg");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("information.jpg");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new Tika().detect(new File("information")));
	}

}
