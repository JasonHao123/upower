package jason.app.weixin.social.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialUser;

public interface ISocialService {

	void saveProfile(SocialUser profile);

	SocialUser loadProfile(Long id);

	@Transactional
	void postPersonalMessage(Long id, String title, String message2, Long type);

	List<Message> getPersonalMessages(Long id, Long category, Pageable pageable);

	Message getMessage(Long userId,Long id);

}
