package jason.app.weixin.social.translator;

import org.springframework.util.StringUtils;

import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialUser;

public class SocialUserTranslator {

	public static SocialUserImpl toEntity(SocialUser profile) {
		SocialUserImpl user = new SocialUserImpl();
		user.setId(profile.getId());
		if(StringUtils.hasText(profile.getAge())) {
			user.setAge(Integer.parseInt(profile.getAge()));
		}
		user.setNickname(profile.getNickname());
		return user;
	}

	public static SocialUser toDTO(SocialUserImpl findOne) {
		// TODO Auto-generated method stub
		if(findOne==null) return null;
		SocialUser socialUser = new SocialUser();
		socialUser.setId(findOne.getId());
		if(findOne.getAge()!=null) {
			socialUser.setAge(""+findOne.getAge());
		}
		if(findOne.getNickname()!=null) {
			socialUser.setNickname(findOne.getNickname());
		}
		return socialUser;
	}

}
