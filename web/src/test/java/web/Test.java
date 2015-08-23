package web;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpClient httpClient =  HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("http://localhost:8080/weixin/processor.do");
		  StringEntity entity = new StringEntity(buildXmlData());
		  // Set XML entity
		  post.setEntity(entity);
		  // Set content type of request header
		  post.setHeader("Content-Type", "text/xml;charset=UTF-8");
		  HttpResponse resp = httpClient.execute(post);
		  String content = EntityUtils.toString(resp.getEntity());
		  System.out.println(content);
	}

	private static String buildXmlData() {
		// TODO Auto-generated method stub
		  StringBuffer sb = new StringBuffer();

		  
		  sb.append("<xml>");
		  sb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
		  sb.append("<FromUserName><![CDATA[oDUuBw4rBU_IJO0Oclrl6JPolu-c]]></FromUserName>");
		  sb.append("<CreateTime>1348831860</CreateTime>");
		  sb.append("<MsgType><![CDATA[text]]></MsgType>");
		  sb.append("<Content><![CDATA[this is a test]]></Content>");
		  sb.append("<MsgId>1234567890123456</MsgId>");
		  sb.append("<Event>subscribe</Event>");
		  
		  sb.append("</xml>");
		// return to String Formed
		  return sb.toString();
	}

}
