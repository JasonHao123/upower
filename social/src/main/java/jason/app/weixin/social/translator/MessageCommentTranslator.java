package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.MessageCommentImpl;
import jason.app.weixin.social.model.MessageComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageCommentTranslator {

	public static List<MessageComment> toDTO(
			List<MessageCommentImpl> result) {
		// TODO Auto-generated method stub
		List<MessageComment> comments  = new ArrayList<MessageComment>();
		for(MessageCommentImpl impl:result) {
			comments.add(toDTO(impl));
		}
		return comments;
	}

	public static MessageComment toDTO(MessageCommentImpl impl) {
		// TODO Auto-generated method stub
		MessageComment comment = new MessageComment(); 
		comment.setId(impl.getId());
		comment.setAuthor(SocialUserTranslator.toDTO(impl.getAuthor()));
		comment.setContent(impl.getContent());
		comment.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(impl.getCreateDate()));
		return comment;
	}

}
