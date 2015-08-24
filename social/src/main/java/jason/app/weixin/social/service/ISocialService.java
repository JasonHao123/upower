package jason.app.weixin.social.service;

import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.Settings;
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

	Message getMessage(Long userId,Long id);

	Integer getSocialDistance(Long id, Long id2);

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

}
