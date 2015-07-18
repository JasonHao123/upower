package jason.app.weixin.social.translator;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.util.ArrayUtil;

public class SocialUserTranslator {

	public static SocialUserImpl toEntity(SocialUser profile) {
		SocialUserImpl user = new SocialUserImpl();
		user.setId(profile.getId());
		user.setAge(profile.getAge());
	
		user.setNickname(profile.getNickname());
		user.setCategory1(profile.getCategory1());
		user.setCategory2(profile.getCategory2());
		user.setHobbys(Arrays.toString(profile.getHobby()));
		user.setLocations(Arrays.toString(profile.getLocation()));
		return user;
	}

	public static SocialUser toDTO(SocialUserImpl findOne) {
		// TODO Auto-generated method stub
		if(findOne==null) return null;
		SocialUser socialUser = new SocialUser();
		socialUser.setId(findOne.getId());

			socialUser.setAge(findOne.getAge());
		
		socialUser.setNickname(findOne.getNickname());
		socialUser.setCategory1(findOne.getCategory1());
		socialUser.setCategory2(findOne.getCategory2());
		socialUser.setHobby(ArrayUtil.toArray(findOne.getHobbys()));
		socialUser.setLocation(ArrayUtil.toArray(findOne.getLocations()));
		return socialUser;
	}

}
