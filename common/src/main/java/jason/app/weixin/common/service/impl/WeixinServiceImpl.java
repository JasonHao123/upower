package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.model.AccessToken;
import jason.app.weixin.common.model.Ticket;
import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.service.IWeixinService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeixinServiceImpl implements IWeixinService{
	
	@Value("#{ systemProperties['accessTokenUrl'] }")
	private String accessTokenUrl;
	
	private String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	
	private String accessToken;
	
	private Date expireDate;
	
	private HttpClient httpClient =  HttpClientBuilder.create().build();
	
	private Calendar calendar = Calendar.getInstance(); 

	private ObjectMapper mapper = new ObjectMapper();

	private String ticket;
	/**
	private void postMessage(SocialMessage msg) {
		// TODO Auto-generated method stub
		try {
			if(!checkAccessToken()) {
				getAccessToken();
			}
			
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
	        HttpPost method = new HttpPost(url);  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        mapper.writeValue(out, msg);
	        StringEntity entity = new StringEntity(out.toString(),"utf-8");//解决中文乱码问题    
	        entity.setContentEncoding("UTF-8");    
	        entity.setContentType("application/json");    
	        method.setEntity(entity);  
	        httpClient.execute(method);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
*/
	private void getAccessToken() {
		// TODO Auto-generated method stub
		
		try {
			HttpGet get = new HttpGet(accessTokenUrl);
			HttpResponse httpResponse = httpClient.execute(get);
			 int statusCode=httpResponse.getStatusLine().getStatusCode();
		        if (statusCode==HttpStatus.SC_OK) {
		        	String content = EntityUtils.toString(httpResponse.getEntity());
		        	AccessToken token = mapper.readValue(content, AccessToken.class);
		        	accessToken = token.getAccess_token();
		        	calendar.setTime(new Date());
		        	calendar.add(Calendar.SECOND, token.getExpires_in());
		        	expireDate = calendar.getTime();
		        }
		        get = new HttpGet(String.format(ticketUrl, accessToken));
		         httpResponse = httpClient.execute(get);
				  statusCode=httpResponse.getStatusLine().getStatusCode();
			        if (statusCode==HttpStatus.SC_OK) {
			        	String content = EntityUtils.toString(httpResponse.getEntity());
			        	Ticket token = mapper.readValue(content, Ticket.class);
			        	ticket = token.getTicket();
			        }
		        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean checkAccessToken() {
		// TODO Auto-generated method stub
		if(accessToken==null || expireDate==null) return false;
		if(expireDate.compareTo(new Date())<0) return false;
		return true;
	}

	@Override
	public WeixinUser getUserInfo(String openId) {
		// TODO Auto-generated method stub
		try {
			if(!checkAccessToken()) {
				getAccessToken();
			}
			
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
	        HttpGet method = new HttpGet(url);  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        HttpResponse response = httpClient.execute(method);
	        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
	        WeixinUser user = mapper.readValue(result, WeixinUser.class);
	      /**  SocialUser socialUser = new SocialUser();
	        socialUser.setNickname(user.getNickname());
	        socialUser.setSex(user.getSex());
	        socialUser.setCountry(user.getCountry());
	        socialUser.setProvince(user.getProvince());
	        socialUser.setCity(user.getCity());
	        socialUser.setHeadimgurl(user.getHeadimgurl());
	        return socialUser;*/
	        return user;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getTicket() {
		// TODO Auto-generated method stub
		try {
			if(!checkAccessToken()) {
				getAccessToken();
			}
			return ticket;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
