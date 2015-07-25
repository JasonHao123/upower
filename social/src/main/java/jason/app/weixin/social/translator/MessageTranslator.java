package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.model.Message;

public class MessageTranslator {

	public static Message toDTO(MessageImpl msg) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setTitle(msg.getTitle());
		message.setAuthor(SocialUserTranslator.toDTO(msg.getAuthor()));
		message.setContent(msg.getContent());
		message.setId(msg.getId());
		return message;
	}

}
