package jason.app.weixin.common;

import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.service.impl.WeixinServiceImpl;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinTest {
	private static Logger logger = LoggerFactory.getLogger(WeixinTest.class);
	private static final String GET_USER_INFO_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
	private HttpClient httpClient =  HttpClientBuilder.create().build();
	private ObjectMapper mapper = new ObjectMapper();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WeixinTest().getUserInfo("opX1TwxhTm-9D2_n_EY_9WZ8K1S8");
	}

	public WeixinUser getUserInfo(String openId) {
		// TODO Auto-generated method stub
		try {
		
			String accessToken = "GNwIHr9hFd6jSZdBgULjZMG9oQNf0KzFtaSBRXPZizKHyLr07jmcw22njySkjPlZXrp87nkVgu59oS5SnjQ_Pp0J0x1ag_sTvlyqsI_AR-4";
			logger.debug("accessToken:"+accessToken );
			logger.debug("openid+"+openId);
			String url = String.format(GET_USER_INFO_TEMPLATE, accessToken,openId);
	        HttpGet method = new HttpGet(url);  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        HttpResponse response = httpClient.execute(method);
	        int statusCode=response.getStatusLine().getStatusCode();
	        if (statusCode==HttpStatus.SC_OK) {
		        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		        logger.info(result);
		        WeixinUser user = mapper.readValue(result, WeixinUser.class);
		        return user;
	        }
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
