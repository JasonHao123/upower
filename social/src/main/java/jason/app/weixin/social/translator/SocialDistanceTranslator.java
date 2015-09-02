package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.model.SocialDistance;

public class SocialDistanceTranslator {

	public static SocialDistance toDTO(SocialDistanceImpl distance) {
		// TODO Auto-generated method stub
		SocialDistance dis = new SocialDistance();
		dis.setFrom(SocialUserTranslator.toDTO(distance.getFromUser()));
		dis.setTo(SocialUserTranslator.toDTO(distance.getToUser()));
		dis.setDistance(distance.getDistance());
		dis.setRating(distance.getRating());
		return dis;
	}

}
