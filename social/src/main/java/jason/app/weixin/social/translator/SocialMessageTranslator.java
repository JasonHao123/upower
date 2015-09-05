package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.SocialMessageImpl;
import jason.app.weixin.social.model.SocialMessage;

public class SocialMessageTranslator {

	public static SocialMessage toDTO(SocialMessageImpl msg) {
		// TODO Auto-generated method stub
		SocialMessage sm = new SocialMessage();
		sm.setId(msg.getId());
		sm.setMessage(MessageTranslator.toDTO(msg.getMessage()));
		sm.setUser(SocialUserTranslator.toDTO(msg.getUser()));
		return sm;
	}

}
