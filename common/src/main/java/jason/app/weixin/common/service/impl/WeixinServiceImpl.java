package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.entity.WeixinConfigImpl;
import jason.app.weixin.common.model.AccessToken;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Ticket;
import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.repository.WeixinConfigRepository;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeixinServiceImpl implements IWeixinService,InitializingBean{
	@Value("#{ systemProperties['appId'] }")
	private String appId;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	private String accessTokenUrl;
	private static final String ACCESS_TOKEN_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	
	private String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	
	private String accessToken;
	
	private Date expireDate;
	
	private HttpClient httpClient =  HttpClientBuilder.create().build();
	
	private Calendar calendar = Calendar.getInstance(); 

	private ObjectMapper mapper = new ObjectMapper();

	private String ticket;

	private String secret;
	
	@Autowired
	private WeixinConfigRepository configRepo;
	@Override
	public void postMessage(SendMessageCommand msg) {
		// TODO Auto-generated method stub
		try {
			if(!checkAccessToken()) {
				refreshAccessToken();
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
				refreshAccessToken();
			}			
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
	        HttpGet method = new HttpGet(url);  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        HttpResponse response = httpClient.execute(method);
	        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
	        WeixinUser user = mapper.readValue(result, WeixinUser.class);
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
				refreshAccessToken();
			}
			return ticket;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getSecret() {

		return secret;
	}
	
	@Override
	@Transactional
	public void refreshAccessToken() {
		WeixinConfigImpl config = configRepo.findOne(appId);
		config.setAccessToken("test");
		configRepo.save(config);
		if(1==1) return;
		calendar.setTime(new Date());
    	calendar.add(Calendar.SECOND, 200);
		if(config.getExpireDate()==null || config.getExpireDate().compareTo(calendar.getTime())<0) {
		try {
			HttpGet get = new HttpGet(accessTokenUrl);
			HttpResponse httpResponse = httpClient.execute(get);
			 int statusCode=httpResponse.getStatusLine().getStatusCode();
		        if (statusCode==HttpStatus.SC_OK) {
		        	String content = EntityUtils.toString(httpResponse.getEntity());
		        	AccessToken token = mapper.readValue(content, AccessToken.class);
		        	accessToken = token.getAccess_token();
		        	config.setAccessToken(accessToken);
		        	calendar.setTime(new Date());
		        	calendar.add(Calendar.SECOND, token.getExpires_in());
		        	expireDate = calendar.getTime();
		        	config.setExpireDate(expireDate);
		        }
		        get = new HttpGet(String.format(ticketUrl, accessToken));
		         httpResponse = httpClient.execute(get);
				  statusCode=httpResponse.getStatusLine().getStatusCode();
			        if (statusCode==HttpStatus.SC_OK) {
			        	String content = EntityUtils.toString(httpResponse.getEntity());
			        	Ticket token = mapper.readValue(content, Ticket.class);
			        	ticket = token.getTicket();
			        	config.setTicket(ticket);
			        }
		configRepo.save(config);        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		WeixinConfigImpl config = configRepo.findOne(appId);
		secret = config.getSecret();
		this.accessTokenUrl = String.format(ACCESS_TOKEN_TEMPLATE, appId,secret);
	}
}
