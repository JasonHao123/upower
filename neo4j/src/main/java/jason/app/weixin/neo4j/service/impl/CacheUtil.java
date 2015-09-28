package jason.app.weixin.neo4j.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

@Named
public class CacheUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(CacheUtil.class);

	private Jedis client = new Jedis("localhost", 6379);

	@PostConstruct
	private void init() {
		client.connect();
	}

	@PreDestroy
	private void destroy() {
		client.close();
	}


//	public User getUser(String accessToken) {
//		logger.info("get user "+accessToken);
//		Map<String, String> userOps = client.hgetAll("user:" + accessToken);
//		User service = null;
//		if (userOps != null) {
//			service = new User();
//			service.setCustId(userOps.get("custId"));
//			service.setOpenId(userOps.get("openId"));
//		}
//		return service;
//	}
//
//	public void putUser(String accessToken, User user) {
//		// TODO Auto-generated method stub
//		logger.info("put user "+accessToken);
//		Map<String, String> userProperties = new HashMap<String, String>();
//		if(user.getCustId()!=null)
//		userProperties.put("custId", user.getCustId());
//		if(user.getOpenId()!=null)
//		userProperties.put("openId", user.getOpenId());
//
//		client.hmset("user:" + accessToken, userProperties);
//	}

	public void setUserExpire(String accessToken) {
		// TODO Auto-generated method stub
		client.expire("user:" + accessToken, 300);
	}


	public void cacheItem(Item item) {
		// TODO Auto-generated method stub
		
	}

}
