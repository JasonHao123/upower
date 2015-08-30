package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.SocialMailImpl;
import jason.app.weixin.social.model.SocialMail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SocialMailTranslator {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
		mail.setCreateDate(format.format(entity.getCreateDate()));
		return mail;
	}

	public static SocialMailImpl toEntity(SocialMail entity) {
		// TODO Auto-generated method stub
		SocialMailImpl mail  = new SocialMailImpl();
		mail.setId(entity.getId());
		mail.setFrom(SocialUserTranslator.toEntity(entity.getFrom()));
		mail.setTo(SocialUserTranslator.toEntity(entity.getTo()));
		mail.setMessage(entity.getMessage());
		mail.setCreateDate(new Date());
		return mail;
	}

}
