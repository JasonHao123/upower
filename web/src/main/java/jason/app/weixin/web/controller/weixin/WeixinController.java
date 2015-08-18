package jason.app.weixin.web.controller.weixin;

import jason.app.weixin.common.model.CreateRelationCommand;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.AddFriendRequestType;
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
import jason.app.weixin.web.controller.social.model.AddFriendForm;

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
@RequestMapping("/weixin")
public class WeixinController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(WeixinController.class);
	

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
	
	String noncestr = "Wm3WZYTPz0wzccnW";
//	String ticket = "kgt8ON7yVITDhtdwci0qedVBEbIY_RWFvlsVv6FEXEcXG5zpJg_m6O82pxkJT3zV4nnQP9j261bZFMdL_JW1Dw";

	String appId = "wxbe821ceae333b377";


	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private ISocialService socialService;

	@RequestMapping(value = "/invite", method = RequestMethod.GET)
	public String invite(HttpServletRequest request,Model model) {
		User currentUser = securityService.getCurrentUser();
		SocialUser profile = socialService.loadProfile(currentUser.getId());
		model.addAttribute("profile", profile);
		UUID uuid  =  UUID.randomUUID();
		model.addAttribute("timeline","0"+ DigestUtils.md5Hex(UUID.randomUUID().toString()));
		model.addAttribute("app", "1"+DigestUtils.md5Hex(UUID.randomUUID().toString()));		
		String timestamp =""+(int) (System.currentTimeMillis() / 1000L);
		StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();
	    String url;
	    if (queryString == null) {
	        url= requestURL.toString();
	    } else {
	        url= requestURL.append('?').append(queryString).toString();
	    }
		String str = "jsapi_ticket="+weixinService.getTicket()+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String signature = DigestUtils.sha1Hex(str);
		model.addAttribute("appId", appId);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("noncestr", noncestr);
		model.addAttribute("signature", signature);
		
		return "weixin.invite";
	}
	
	@RequestMapping(value = "/invite2", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody String invite2(@RequestParam("id") String id) {
		User currentUser = securityService.getCurrentUser();
		socialService.createAddFriendLink(currentUser.getId(),id);
		return "test";
	}
	

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	@Transactional
	public String home(Model model,
			@RequestParam(required = false, value = "id") String id) {
		logger.info("Welcome home!");
		User user = securityService.getCurrentUser();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		if (StringUtils.isEmpty(id)) {
			SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
			AddFriendLinkImpl link = linkRepo
					.findByUser_IdAndCreateDateGreaterThan(user.getId(),
							calendar.getTime());
			if (link == null) {
				link = new AddFriendLinkImpl();
				link.setUser(userImpl);
				link.setCreateDate(new Date());
				link = linkRepo.save(link);
			}
			return "redirect:/social/addfriend.do?id=" + link.getId();
		} else {
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

		}

		return "weixin.addfriend";
	}
	
	
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	@Transactional
	public String postAddFriend(Model model,final AddFriendForm addFriendForm, BindingResult result) {
		final User user = securityService.getCurrentUser();
		// from
		SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
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
				request.setMessage(userImpl.getNickname() +" accept your add friend link, he also want to add you as friend!");
				request.setType(AddFriendRequestType.CONFIRM);
				request.setCreateDate(new Date());
				addFriendRequestRepo.save(request);
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
		return "redirect:/weixin/accept.do?id="+addFriendForm.getId()+"&saved=true";
	}
}
