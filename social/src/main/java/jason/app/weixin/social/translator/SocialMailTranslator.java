package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.SocialMailImpl;
import jason.app.weixin.social.model.SocialMail;

import java.util.ArrayList;
import java.util.List;

public class SocialMailTranslator {

	public static List<SocialMail> toDTO(
			List<SocialMailImpl> entities) {
		// TODO Auto-generated method stub
		List<SocialMail> mails = new ArrayList<SocialMail>();
		if(entities!=null) {
			for(SocialMailImpl entity:entities) {
				mails.add(toDTO(entity));
			}
		}
		return mails;
	}

	public static SocialMail toDTO(SocialMailImpl entity) {
		// TODO Auto-generated method stub
		SocialMail mail  = new SocialMail();
		mail.setId(entity.getId());
		mail.setFrom(SocialUserTranslator.toDTO(entity.getFrom()));
		mail.setTo(SocialUserTranslator.toDTO(entity.getTo()));
		mail.setMessage(entity.getMessage());
		return mail;
	}

}
