package jason.app.weixin.web.controller;

import jason.app.weixin.social.model.SocialMessage;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.web.controller.weixin.model.AccessToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.http.HttpEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

public class ExampleListener implements MessageListener {
	
	@Value("#{ systemProperties['accessTokenUrl'] }")
	private String accessTokenUrl;
	
	@Autowired
	private SocialDistanceRepository distanceRepo;
	
	@Autowired
	private SocialUserRepository userRepo;
	
	private String accessToken;
	
	private Date expireDate;
	
	private HttpClient httpClient =  HttpClientBuilder.create().build();
	
	private Calendar calendar = Calendar.getInstance(); 

	private ObjectMapper mapper = new ObjectMapper();
	@Transactional
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }else if(message instanceof ObjectMessage) {
        	ObjectMessage hmsg = (ObjectMessage)message;
        	try {
				System.out.println(hmsg.getObject());
				SocialMessage msg = (SocialMessage)hmsg;
				postMessage(msg);
/**				SocialRelationDTO dto = (SocialRelationDTO)hmsg.getObject();
				if(dto.getFrom()!=null && dto.getTo()!=null) {
					SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(dto.getFrom(), dto.getTo());
					if(distance==null) {
						distance = new SocialDistanceImpl();
						distance.setFromUser(userRepo.findOne(dto.getFrom()));
						distance.setToUser(userRepo.findOne(dto.getTo()));
					}
					distance.setDistance(dto.getDistance());
					distanceRepo.save(distance);
				}
*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }

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

	private void getAccessToken() {
		// TODO Auto-generated method stub
		HttpGet get = new HttpGet(accessTokenUrl);
		try {
			HttpResponse httpResponse = httpClient.execute(get);
			 int statusCode=httpResponse.getStatusLine().getStatusCode();
		        if (statusCode==HttpStatus.SC_OK) {
		        	String content = EntityUtils.toString(httpResponse.getEntity());
		        	AccessToken token = mapper.readValue(content, AccessToken.class);
		        	accessTokenUrl = token.getAccess_token();
		        	calendar.setTime(new Date());
		        	calendar.add(Calendar.SECOND, token.getExpires_in());
		        	expireDate = calendar.getTime();
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
}
