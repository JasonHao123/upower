package jason.app.weixin.web.service.impl;

import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.entity.FriendRelationshipImpl;
import jason.app.weixin.social.repository.FriendRelationshipRepository;
import jason.app.weixin.web.service.Rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class NoFriendRule extends Rule{

	@Autowired
	private FriendRelationshipRepository repo;
	
	@Autowired
	private ISecurityService securityService;
	
	@Override
	public String check() {
		User user = securityService.getCurrentUser();
		List<FriendRelationshipImpl> friends = repo.findByFrom_Id(user.getId());
		if(friends==null || friends.size()==0) return "redirect:/social/addfriend.do";
		return null;
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
