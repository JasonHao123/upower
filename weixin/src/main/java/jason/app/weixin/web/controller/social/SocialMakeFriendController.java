package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.model.CreateRelationCommand;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Text;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.common.util.CommonUtil;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.AddFriendRequestType;
import jason.app.weixin.social.constant.Status;
import jason.app.weixin.social.entity.AddFriendLinkImpl;
import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.AddFriendLinkRepository;
import jason.app.weixin.social.repository.AddFriendRequestRepository;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.social.util.ArrayUtil;
import jason.app.weixin.web.controller.weixin.model.AddFriendForm;
import jason.app.weixin.web.controller.weixin.model.AddFriendRequestForm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/social")
public class SocialMakeFriendController {
	private static final Logger logger = LoggerFactory
			.getLogger(SocialMakeFriendController.class);
	

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private SocialUserRepository socailUserRepo;
	
	@Autowired
	private SocialRelationshipRepository socialRelationRepo;

	@Autowired
	private AddFriendLinkRepository linkRepo;
	
	@Autowired
	private AddFriendRequestRepository addFriendRequestRepo;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IWeixinService weixinService;


	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private ISocialService socialService;
	
	/**
	 * Display invite friend page
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invite", method = RequestMethod.GET)
	public String invite(HttpServletRequest request,Model model) {
		User currentUser = securityService.getCurrentUser();
		SocialUser profile = socialService.loadProfile(currentUser.getId());
		model.addAttribute("profile", profile);
		UUID uuid  =  UUID.randomUUID();
		model.addAttribute("timeline","0"+ DigestUtils.md5Hex(UUID.randomUUID().toString()));
		model.addAttribute("app", "1"+DigestUtils.md5Hex(UUID.randomUUID().toString()));		
		
		return "social.invite";
	}
	
	/**
	 * callback from UI(ajax) to save the invite link
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/invite2", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody String invite2(@RequestParam("id") String id) {
		User currentUser = securityService.getCurrentUser();
		socialService.createAddFriendLink(currentUser.getId(),id);
		return "test";
	}
	
	
	@RequestMapping(value = "/replyrequest", method = RequestMethod.GET)
	@Transactional
	public  String replyrequest(Model model,@RequestParam("id") Long id) {
    	User user = securityService.getCurrentUser();
    	AddFriendRequestImpl request = addFriendRequestRepo.findOne(id);
    	if(request!=null && request.getTo().getId()==user.getId()) {
    		model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));
    		AddFriendRequestForm form = new AddFriendRequestForm();
    		form.setId(request.getId());
    		model.addAttribute("addFriendRequestForm",form);
    		model.addAttribute("user",socialService.loadProfile(request.getFrom().getId()));
    		return "social.reply.friend.add";
    	}else {
    		throw new AccessDeniedException("no access to the request");
    	}
	}
	
	
	@RequestMapping(value = "/replyrequest", method = RequestMethod.POST)
	@Transactional
	public String postReplyAddFriend(Model model,final AddFriendRequestForm addFriendRequestForm, BindingResult result) {
		User user = securityService.getCurrentUser();
		final SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
		final SocialUserImpl toUser = socailUserRepo.findOne(addFriendRequestForm.getUserId());
		
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
			
			final AddFriendRequestImpl request = addFriendRequestRepo.findOne(addFriendRequestForm.getId());
			if(AddFriendRequestType.REQUEST==request.getType()) {
				SocialRelationshipImpl reverse = socialRelationRepo.findByFrom_IdAndTo_Id(toUser.getId(),user.getId());
				if(reverse==null) {
					reverse = new SocialRelationshipImpl();
					reverse.setId(toUser.getId()+"_"+userImpl.getId());
					reverse.setTo(userImpl);
					reverse.setFrom(toUser);
				}
				reverse.setLastUpdate(new Date());
				reverse.setRating(request.getRating());
				reverse.setRelationType(request.getFriendType());
				socialRelationRepo.save(reverse);
				jmsTemplate.send(new MessageCreator() {
		            public Message createMessage(Session session) throws JMSException {
		              //  return session.createTextMessage("hello queue world");
		            	CreateRelationCommand command = new CreateRelationCommand();
		            	command.setToUser(userImpl.getId());
		            	command.setFromUser(toUser.getId());
		            	command.setTypes(ArrayUtil.toLongArray(request.getFriendType()));
		            	command.setRating(request.getRating());
		            	return session.createObjectMessage(command);
		              }
		          });
				
			}
			request.setStatus(Status.CLOSED);
			addFriendRequestRepo.save(request);
			
			
			jmsTemplate.send(new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");
	            	CreateRelationCommand command = new CreateRelationCommand();
	            	command.setFromUser(userImpl.getId());
	            	command.setToUser(toUser.getId());
	            	command.setTypes(addFriendRequestForm.getFriendshipType());
	            	command.setRating(addFriendRequestForm.getRating());
	            	return session.createObjectMessage(command);
	              }
	          });
		return "redirect:/social/friends.do";
	}

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	@Transactional
	public String home(Model model,
			@RequestParam(required = true, value = "id") String id) {
		logger.info("Welcome home!");
		User user = securityService.getCurrentUser();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);

			AddFriendLinkImpl link = linkRepo.findOne(id);

			boolean expired = calendar.getTime()
					.compareTo(link.getCreateDate()) >= 0;
			model.addAttribute("link", link);
			model.addAttribute("expired", expired);
			boolean self = link.getUser().getId().equals(user.getId());
			model.addAttribute("self",self);
			AddFriendForm form = new AddFriendForm();
			if(!self) {
				SocialRelationshipImpl relation = socialRelationRepo.findByFrom_IdAndTo_Id(user.getId(),link.getUser().getId());
				if(relation!=null) {
				form.setRating(relation.getRating());
				form.setFriendshipType(ArrayUtil.toLongArray(relation.getRelationType()));
				}else {
					form.setRating(3F);
				}
			}else {
				form.setRating(3f);
			}
			model.addAttribute("addFriendForm",form);
			SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
			model.addAttribute("hasProfile",user!=null);
			model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));


		return "social.addfriend";
	}
	
	
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	@Transactional
	public String postAddFriend(Model model,final AddFriendForm addFriendForm, BindingResult result) {
		final User user = securityService.getCurrentUser();
		// from
		final SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
		
		final AddFriendLinkImpl link = linkRepo.findOne(addFriendForm.getId());
		SocialRelationshipImpl relation = socialRelationRepo.findByFrom_IdAndTo_Id(user.getId(),link.getUser().getId());
		if(relation==null) {
			relation = new SocialRelationshipImpl();
			relation.setId(userImpl.getId()+"_"+link.getUser().getId());
			relation.setFrom(userImpl);
			relation.setTo(link.getUser());
			
			// check whether reverse relation exists
			SocialRelationshipImpl reverse = socialRelationRepo.findByFrom_IdAndTo_Id(link.getUser().getId(), userImpl.getId());
			if(reverse==null) {
				// TODO something here
				AddFriendRequestImpl request = new AddFriendRequestImpl();
				request.setFrom(userImpl);
				request.setTo(link.getUser());
				request.setMessage(userImpl.getNickname() +" 接受了你的好友申请，"+CommonUtil.getHeOrShe(userImpl.getSex())+"也想添加你为好友!");
				request.setType(AddFriendRequestType.CONFIRM);
				request.setCreateDate(new Date());
				request = addFriendRequestRepo.save(request);
				final Long id = request.getId();
				jmsTemplate.send(new MessageCreator() {
		            public Message createMessage(Session session) throws JMSException {
		              //  return session.createTextMessage("hello queue world");
		            	SendMessageCommand command = new SendMessageCommand();
		            	command.setMsgtype("text");
		            	command.setTouser(link.getUser().getOpenid());
		            	command.setText(new Text(userImpl.getNickname() +" 接受了你的好友申请，"+CommonUtil.getHeOrShe(userImpl.getSex())+"也想添加你为好友! 点击以下链接接受请求。<a href=\"http://www.weaktie.cn/weixin/replyrequest.do?id="+id+"\">接受</a>"));
		            	return session.createObjectMessage(command);
		              }
		          });
			}
		}
		relation.setLastUpdate(new Date());
		relation.setRating(addFriendForm.getRating());
		relation.setRelationType(Arrays.toString(addFriendForm.getFriendshipType()));
		socialRelationRepo.save(relation);

		// dfggffg
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              //  return session.createTextMessage("hello queue world");
            	CreateRelationCommand command = new CreateRelationCommand();
            	command.setFromUser(user.getId());
            	command.setToUser(link.getUser().getId());
            	command.setTypes(addFriendForm.getFriendshipType());
            	command.setRating(addFriendForm.getRating());
            	return session.createObjectMessage(command);
              }
          });
	/**			*/
		return "redirect:/social/accept.do?id="+addFriendForm.getId()+"&saved=true";
	}
	
	
	
    @RequestMapping(value="/addfriend",method=RequestMethod.GET)
    public String addFriend(Model model,
			@RequestParam(required = false, value = "id") Long id) {
    	User user = securityService.getCurrentUser();
    	String page = "social.friend.add";
    	if(socialService.isFriend(user.getId(),id)) {
    		page = "social.already.friend";
    	}
		model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));
		AddFriendRequestForm form = new AddFriendRequestForm();
		form.setUserId(id);
		form.setRating(3F);
		model.addAttribute("self",false);
		model.addAttribute("expired",false);
		SocialUser profile = socialService.loadProfile(id);
		model.addAttribute("hasProfile",profile!=null);
		model.addAttribute("addFriendForm",form);
		model.addAttribute("user",profile);
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
		request = addFriendRequestRepo.save(request);
		if(StringUtils.hasText(toUser.getOpenid())) {
			SendMessageCommand command = new SendMessageCommand();
			command.setMsgtype("text");
			command.setTouser(toUser.getOpenid());
			command.setText(new Text(userImpl.getNickname()+"正在申请添加您为好友，点击以下链接添加好友。<a href=\"http://www.weaktie.cn/weixin/social/replyrequest.do?id="+request.getId()+"\">同意添加</a>"));
			weixinService.postMessage(command);
		}
		
		return "redirect:/social/home.do?id="+addFriendRequestForm.getUserId();
	}
}
