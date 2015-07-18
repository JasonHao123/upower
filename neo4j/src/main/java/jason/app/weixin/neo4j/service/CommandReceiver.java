package jason.app.weixin.neo4j.service;

import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserRepository;
import jason.app.weixin.social.api.command.ICommandReceiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandReceiver implements ICommandReceiver{

	@Autowired
	private SocialUserRepository userRepo;
	
	@Override
	@Transactional
	public void createUser(Long userId, String nickname, Integer age) {
		// TODO Auto-generated method stub
		SocialUser user = userRepo.findByUserId(userId);
		if(user==null) {
			user = new SocialUser();
		}
		user.setName(nickname);
		user.setUserId(userId);
		user.setAge(age);
		userRepo.save(user);
	}

}
