package jason.app.weixin.web.controller.user;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.AddFriendRequestType;
import jason.app.weixin.social.constant.Status;
import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.AddFriendRequestRepository;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.user.model.AddFriendRequestForm;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SocialRelationshipRepository socialRelationRepo;
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private SocialUserRepository socailUserRepo;
	
	@Autowired
	private AddFriendRequestRepository addFriendRequestRepo;
	
	  private static final Logger logger = LoggerFactory
	            .getLogger(UserController.class);

    @RequestMapping("/index")
    public String home() {
        return "user.home";
    }

    @RequestMapping("/friends")
    public String friends() {
        return "user.friend.list";
    }
    
    @RequestMapping("/circle")
    public String circle() {
        return "user.circle.list";
    }
    
    @RequestMapping("/mails")
    public String mails() {
        return "user.mail.list";
    }
    
    @RequestMapping("/requests")
    public String requests() {
        return "user.request.list";
    }

	@RequestMapping(value = "/friends2", method = RequestMethod.GET)
	public @ResponseBody List<SocialUser> messages(@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		
		return socialService.getFriends(user.getId(),pageable);

	}
	
	@RequestMapping(value = "/circle2", method = RequestMethod.GET)
	public @ResponseBody List<SocialUser> circle(@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		
		return socialService.getCircle(user.getId(),pageable);

	}
	
	@RequestMapping(value = "/requests2", method = RequestMethod.GET)
	public @ResponseBody List<AddFriendRequest> requests2(@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		
		return socialService.getMyAddFriendRequests(user.getId(),pageable);

	}
    
    @RequestMapping("/follow")
    public String follow() {
        return "user.profile";
    }
    
    @RequestMapping(value="/addfriend",method=RequestMethod.GET)
    public String addFriend(Model model,
			@RequestParam(required = false, value = "id") Long id) {
    	User user = securityService.getCurrentUser();
    	String page = "user.friend.add";
    	if(socialService.isFriend(user.getId(),id)) {
    		page = "user.already.friend";
    	}
		model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));
		AddFriendRequestForm form = new AddFriendRequestForm();
		model.addAttribute("addFriendRequestForm",form);
		model.addAttribute("user",socialService.loadProfile(id));
        return page;
    }
    
	@RequestMapping(value = "/addfriend", method = RequestMethod.POST)
	@Transactional
	public String postAddFriend(Model model,final AddFriendRequestForm addFriendRequestForm, BindingResult result) {
		User user = securityService.getCurrentUser();
		SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
		SocialUserImpl toUser = socailUserRepo.findOne(addFriendRequestForm.getUserId());
		AddFriendRequestImpl request = new AddFriendRequestImpl();
		request.setFrom(userImpl);
		request.setTo(toUser);
		request.setMessage(addFriendRequestForm.getMessage());
		request.setType(AddFriendRequestType.REQUEST);
		request.setRating(addFriendRequestForm.getRating());
		request.setFriendType(Arrays.toString(addFriendRequestForm.getFriendshipType()));
		request.setCreateDate(new Date());
		addFriendRequestRepo.save(request);
		return "redirect:/user/friends.do";
	}
	
	
	
    @RequestMapping(value="/replyrequest",method=RequestMethod.GET)
    public String replyrequest(Model model,
			@RequestParam(required = false, value = "id") Long id) {
    	User user = securityService.getCurrentUser();
    	AddFriendRequestImpl request = addFriendRequestRepo.findOne(id);
    	if(request!=null && request.getTo().getId()==user.getId()) {
    		model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));
    		AddFriendRequestForm form = new AddFriendRequestForm();
    		form.setId(request.getId());
    		model.addAttribute("addFriendRequestForm",form);
    		model.addAttribute("user",socialService.loadProfile(request.getFrom().getId()));
    		return "user.reply.friend.add";
    	}else {
    		throw new AccessDeniedException("no access to the request");
    	}

    }
    
    
	@RequestMapping(value = "/replyrequest", method = RequestMethod.POST)
	@Transactional
	public String postReplyAddFriend(Model model,final AddFriendRequestForm addFriendRequestForm, BindingResult result) {
		User user = securityService.getCurrentUser();
		SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
		SocialUserImpl toUser = socailUserRepo.findOne(addFriendRequestForm.getUserId());
		
		SocialRelationshipImpl relation = socialRelationRepo.findByFrom_IdAndTo_Id(user.getId(),toUser.getId());
		if(relation==null) {
			relation = new SocialRelationshipImpl();
			relation.setId(userImpl.getId()+"_"+toUser.getId());
			relation.setFrom(userImpl);
			relation.setTo(toUser);
		}
			relation.setLastUpdate(new Date());
			relation.setRating(addFriendRequestForm.getRating());
			relation.setRelationType(Arrays.toString(addFriendRequestForm.getFriendshipType()));
			socialRelationRepo.save(relation);
			AddFriendRequestImpl request = addFriendRequestRepo.findOne(addFriendRequestForm.getId());
			if(AddFriendRequestType.REQUEST==request.getType()) {
				SocialRelationshipImpl reverse = socialRelationRepo.findByFrom_IdAndTo_Id(toUser.getId(),user.getId());
				if(reverse==null) {
					reverse = new SocialRelationshipImpl();
					reverse.setId(toUser.getId()+"_"+userImpl.getId());
					reverse.setTo(userImpl);
					reverse.setFrom(toUser);
				}
				relation.setLastUpdate(new Date());
				relation.setRating(request.getRating());
				relation.setRelationType(request.getFriendType());
				socialRelationRepo.save(relation);
			}
			request.setStatus(Status.CLOSED);
			addFriendRequestRepo.save(request);
		return "redirect:/user/friends.do";
	}
}
