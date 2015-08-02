package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.AddFriendRequestType;
import jason.app.weixin.social.entity.AddFriendLinkImpl;
import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.AddFriendLinkRepository;
import jason.app.weixin.social.repository.AddFriendRequestRepository;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.util.ArrayUtil;
import jason.app.weixin.web.controller.social.model.AddFriendForm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/social")
public class SocialController {
	
//	@Autowired
//	private JmsTemplate jmsTemplate;

	@Autowired
	private ISecurityService service;

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

	private static final Logger logger = LoggerFactory
			.getLogger(SocialController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/addfriend", method = RequestMethod.GET)
	@Transactional
	public String home(Model model,
			@RequestParam(required = false, value = "id") String id) {
		logger.info("Welcome home!");
		User user = service.getCurrentUser();
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

		return "social.addfriend";
	}
	
	
	@RequestMapping(value = "/addfriend", method = RequestMethod.POST)
	@Transactional
	public String postAddFriend(Model model,final AddFriendForm addFriendForm, BindingResult result) {
		final User user = service.getCurrentUser();
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
/**		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              //  return session.createTextMessage("hello queue world");
            	AddRelationCommand command = new AddRelationCommand();
            	command.setFromUser(user.getId());
            	command.setToUser(link.getUser().getId());
            	command.setTypes(addFriendForm.getFriendshipType());
            	command.setRating(addFriendForm.getRating());
            	return session.createObjectMessage(command);
              }
          });
		*/
		return "redirect:/user/index.do";
	}
	
	
}
