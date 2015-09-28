package jason.app.weixin.social.service;

import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.model.Comment;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.MessageComment;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.model.SocialDistance;
import jason.app.weixin.social.model.SocialMail;
import jason.app.weixin.social.model.SocialMessage;
import jason.app.weixin.social.model.SocialRelationDTO;
import jason.app.weixin.social.model.SocialUser;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ISocialService {

	@Transactional
	void saveProfile(SocialUser profile);

	SocialUser loadProfile(Long id);

	@Transactional
	void postPersonalMessage(Long id, String title, String message2, Long type);

	List<Message> getPersonalMessages(Long id, Long category, Pageable pageable);

	Message getSocialMessage(Long userId,Long id);

	SocialDistance getSocialDistance(Long id, Long id2);

	List<Message> getUserMessages(Long id, Pageable pageable);

	Message getMessage2(Long id, Long id2);
	
	Settings getUserSettings(Long id);

	@Transactional
	void saveSettings(Settings settings);

	List<SocialUser> getFriends(Long user, Pageable pageable);

	List<SocialUser> getCircle(Long id, Pageable pageable);

	boolean isFriend(Long id, Long id2);

	List<AddFriendRequest> getMyAddFriendRequests(Long id, Pageable pageable);

	@Transactional
	void saveDistance(SocialRelationDTO dto);

	@Transactional
	void createAddFriendLink(Long id, String id2);

	List<SocialMail> getUserConversation(List<Long> users, Pageable pageable);

	SocialUser loadProfileByOpenId(String openid);

	void saveAnalyzeResult(AnalyzeResult result);

	List<Comment> getUserComments(Long id, Pageable pageable);

	Comment saveComment(Comment comment);

	SocialMail saveMail(SocialMail comment);

	Float getUserRating(Long id);

	AnalyzeResult getAnalyzeResult(String key);

	SocialUser findByExternalId(String fromUserName);

	jason.app.weixin.social.model.Message getMessage(Long id, Long id2);

	Message saveMessage(Message form);

	void publishMessage(Long messageId);

	SocialMessage publishMessageToUser(Long messageId, Long userId);

	List<MessageComment> getMessageComments(Long id, Long id2, Pageable pageable);

	MessageComment saveMessageComment(MessageComment comment);

	void saveSnippet(Long id, String content);

}
