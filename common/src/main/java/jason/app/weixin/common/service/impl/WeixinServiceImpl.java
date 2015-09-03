package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.entity.WeixinConfigImpl;
import jason.app.weixin.common.model.AccessToken;
import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Ticket;
import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.repository.WeixinConfigRepository;
import jason.app.weixin.common.service.IWeixinService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeixinServiceImpl implements IWeixinService, InitializingBean {
	private static Logger logger = LoggerFactory
			.getLogger(WeixinServiceImpl.class);

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
	private static final String GET_USER_INFO_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
	private String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	private static final String DOWNLOAD_MEDIA_TEMPLATE = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	private String accessToken;

	private Date expireDate;

	private HttpClient httpClient = HttpClients.createSystem(); // HttpClientBuilder.create()..build();

	private Calendar calendar = Calendar.getInstance();

	private ObjectMapper mapper = new ObjectMapper();

	private String ticket;

	private String secret;

	private RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(5000).setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000).build();

	@Autowired(required = false)
	private WeixinConfigRepository configRepo;

	@Override
	public void postMessage(SendMessageCommand msg) throws Exception {

		if (!checkAccessToken()) {
			refreshAccessToken();
		}

		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ accessToken;
		HttpPost method = new HttpPost(url);
		method.setConfig(requestConfig);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, msg);
		StringEntity entity = new StringEntity(out.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		method.setEntity(entity);
		HttpResponse resp = httpClient.execute(method);
		EntityUtils.consume(resp.getEntity());

	}

	private boolean checkAccessToken() {
		// TODO Auto-generated method stub
		if (accessToken == null || expireDate == null)
			return false;
		if (expireDate.compareTo(new Date()) < 0)
			return false;
		return true;
	}

	@Override
	public WeixinUser getUserInfo(String openId) {
		// TODO Auto-generated method stub
		try {
			if (!checkAccessToken()) {
				refreshAccessToken();
			}
			logger.debug("accessToken:" + accessToken);
			logger.debug("openid+" + openId);
			String url = String.format(GET_USER_INFO_TEMPLATE, accessToken,
					openId);
			HttpGet method = new HttpGet(url);
			method.setConfig(requestConfig);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			HttpResponse response = httpClient.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				logger.info(result);
				WeixinUser user = mapper.readValue(result, WeixinUser.class);
				return user;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public String getTicket() {
		// TODO Auto-generated method stub
		try {
			if (!checkAccessToken()) {
				refreshAccessToken();
			}
			return ticket;
		} catch (Exception e) {
			logger.error("failed to refresh access token due to "
					+ e.getMessage());
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
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 200);
		if (config.getExpireDate() == null
				|| config.getExpireDate().compareTo(calendar.getTime()) < 0) {
			try {
				HttpGet get = new HttpGet(accessTokenUrl);
				get.setConfig(requestConfig);
				HttpResponse httpResponse = httpClient.execute(get);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					String content = EntityUtils.toString(httpResponse
							.getEntity());
					AccessToken token = mapper.readValue(content,
							AccessToken.class);
					accessToken = token.getAccess_token();
					logger.info("access token" + accessToken);
					config.setAccessToken(accessToken);
					calendar.setTime(new Date());
					calendar.add(Calendar.SECOND, token.getExpires_in());
					expireDate = calendar.getTime();
					config.setExpireDate(expireDate);
				}
				get = new HttpGet(String.format(ticketUrl, accessToken));
				get.setConfig(requestConfig);
				httpResponse = httpClient.execute(get);
				statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					String content = EntityUtils.toString(httpResponse
							.getEntity());
					Ticket token = mapper.readValue(content, Ticket.class);
					ticket = token.getTicket();
					config.setTicket(ticket);
				}
				configRepo.save(config);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("failed to refresh access token " + e.getMessage());
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (configRepo != null) {
			WeixinConfigImpl config = configRepo.findOne(appId);
			secret = config.getSecret();
			this.accessTokenUrl = String.format(ACCESS_TOKEN_TEMPLATE, appId,
					secret);
			this.accessToken = config.getAccessToken();
			this.expireDate = config.getExpireDate();
			this.ticket = config.getTicket();
		}
	}

	@Override
	public FileInfo downloadMedia(String mediaId) throws Exception {
		FileInfo fileInfo = new FileInfo();
		String url = String.format(DOWNLOAD_MEDIA_TEMPLATE, accessToken,
				mediaId);
		File output = File.createTempFile("weaktie_", ".tmp");
		logger.info("download media URL:"+url);
		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);
		HttpResponse httpResponse = httpClient.execute(get);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			Header contentType = httpResponse.getFirstHeader("Content-Type");
			if(contentType!=null) {
				fileInfo.setContentType(contentType
					.getValue());
			}
			logger.info("Content-Type:"+fileInfo.getContentType());
			fileInfo.setFileName(determinFileNameFromHeader(httpResponse.getFirstHeader("Content-disposition")));
			httpResponse.getEntity().writeTo(new FileOutputStream(output));
			fileInfo.setFile(output);
		}
		EntityUtils.consume(httpResponse.getEntity());
		return fileInfo;
	}

	public String determinFileNameFromHeader(Header header) {
		// TODO Auto-generated method stub
		if(header!=null) {
			String str = header.getValue();
			int startPos = str.indexOf("\"");
			int endPos = str.lastIndexOf("\"");
			if(startPos>0 && endPos>startPos) {
				return str.substring(startPos+1,endPos);
			}
		}
		return null;
	}

}
