package jason.app.weixin.neo4j.service;

import jason.app.weixin.social.model.SocialUser;

public interface IWeixinService {

	SocialUser getUserInfo(String openId);

}
