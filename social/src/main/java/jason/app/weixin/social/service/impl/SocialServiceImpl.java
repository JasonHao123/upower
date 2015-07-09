package jason.app.weixin.social.service.impl;

import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.social.translator.SocialUserTranslator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocialServiceImpl implements ISocialService {

	@Autowired
	private SocialUserRepository socialUserRepo;
	@Override
	@Transactional
	public void saveProfile(SocialUser profile) {
		// TODO Auto-generated method stub
		SocialUserImpl user = SocialUserTranslator.toEntity(profile);
		socialUserRepo.save(user);
	}

	@Override
	public SocialUser loadProfile(Long id) {
		// TODO Auto-generated method stub
		return SocialUserTranslator.toDTO(socialUserRepo.findOne(id));
	}

}
