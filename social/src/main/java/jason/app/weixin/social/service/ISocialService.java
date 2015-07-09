package jason.app.weixin.social.service;

import jason.app.weixin.social.model.SocialUser;

public interface ISocialService {

	void saveProfile(SocialUser profile);

	SocialUser loadProfile(Long id);

}
