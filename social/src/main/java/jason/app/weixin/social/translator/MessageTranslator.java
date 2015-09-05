package jason.app.weixin.social.translator;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.model.Message;

import java.text.SimpleDateFormat;

public class MessageTranslator {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static Message toDTO(MessageImpl msg) {
		// TODO Auto-generated method stub
		if(msg==null) return null;
		Message message = new Message();
		message.setTitle(msg.getTitle());
		message.setAuthor(SocialUserTranslator.toDTO(msg.getAuthor()));
		message.setContent(msg.getContent());
		message.setId(msg.getId());
		if(msg.getCategory()!=null) {
			message.setCategory(new Category(msg.getCategory()));
		}
//		message.setCategory(msg.getCategory());
		message.setDistance(msg.getTargetDistance());
		message.setMaxAge(msg.getTargetMaxAge());
		message.setMinAge(msg.getTargetMinAge());
		message.setRating(msg.getTargetRating());
		message.setSex(msg.getTargetSex());
		message.setStatus(msg.getStatus());
		if(msg.getLastUpdate()!=null) {
			message.setLastUpdate(format.format(msg.getLastUpdate()));
		}
		return message;
	}

	public static MessageImpl toEntity(Message msg) {
		if(msg==null) return null;
		MessageImpl message = new MessageImpl();
		message.setTitle(msg.getTitle());
	//	message.setAuthor(SocialUserTranslator.toEntity(msg.getAuthor()));
		message.setContent(msg.getContent());
		message.setId(msg.getId());
		if(msg.getCategory()!=null) {
			message.setCategory(msg.getCategory().getId());
		}
		message.setTargetDistance(msg.getDistance());
		message.setTargetMaxAge(msg.getMaxAge());
		message.setTargetMinAge(msg.getMinAge());
		message.setTargetRating(msg.getRating());
		message.setTargetSex(msg.getSex());
		message.setStatus(msg.getStatus());
		return message;
	}

}
