package jason.app.weixin.social.api.command;

import org.springframework.transaction.annotation.Transactional;


public interface ICommandReceiver {
	@Transactional
	void createUser(Long userId, String nickname, Integer age);

}
