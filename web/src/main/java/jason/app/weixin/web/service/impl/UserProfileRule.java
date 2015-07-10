package jason.app.weixin.web.service.impl;

import jason.app.weixin.security.entity.UserImpl;
import jason.app.weixin.security.repository.UserRepository;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.web.service.Rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRule extends Rule {

	@Autowired
	private SocialUserRepository socialUserRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public String check() {
		// TODO Auto-generated method stub
		String username =  SecurityContextHolder.getContext().getAuthentication().getName();
		UserImpl user = userRepo.findByUsername(username);
		if(user!=null) {
			Long id = user.getId();
			SocialUserImpl user2 = socialUserRepo.findOne(id);
			if(user2==null) return "redirect:/user/profile/edit.do";
		}
		return null;
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
