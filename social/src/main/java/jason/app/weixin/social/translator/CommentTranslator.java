package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.CommentImpl;
import jason.app.weixin.social.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentTranslator {

	public static List<Comment> toDTO(
			List<CommentImpl> comments) {
		// TODO Auto-generated method stub
		List<Comment> result = new ArrayList<Comment>();
		for(CommentImpl comment:comments) {
			result.add(toDTO(comment));
		}
		
		return result;
	}

	public static Comment toDTO(CommentImpl comment) {
		// TODO Auto-generated method stub
		Comment result = new Comment();
		result.setId(comment.getId());
		result.setAuthor(SocialUserTranslator.toDTO(comment.getAuthor()));
		result.setTarget(SocialUserTranslator.toDTO(comment.getTarget()));
		result.setMessage(comment.getMessage());
		result.setRating(comment.getRating());
		return result;
	}

	public static CommentImpl toEntity(Comment comment) {
		// TODO Auto-generated method stub
		CommentImpl result = new CommentImpl();
		result.setId(comment.getId());
		result.setAuthor(SocialUserTranslator.toEntity(comment.getAuthor()));
		result.setTarget(SocialUserTranslator.toEntity(comment.getTarget()));
		result.setMessage(comment.getMessage());
		result.setRating(comment.getRating());
		return result;
	}

}
