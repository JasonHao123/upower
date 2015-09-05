package jason.app.weixin.web.controller.circle;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.model.PublishMessageCommand;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.Status;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialMessage;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.circle.model.PostMessageForm;
import jason.app.weixin.web.controller.circle.model.PostMessageValidator;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/circle")
public class CircleController {

	private static final Logger logger = LoggerFactory
			.getLogger(CircleController.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
		
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ISocialService socialService;

	@Autowired
	private ISecurityService securityService;
	
	private PostMessageValidator validator = new PostMessageValidator();
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String message(Model model,@RequestParam(value="id",required=true) Long id) {
		logger.info("Welcome home!");
		User user = securityService.getCurrentUser();
		model.addAttribute("message",socialService.getSocialMessage(user.getId(), id));
		return "circle.message";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String home(Model model,@RequestParam(value="id",required=false) Long id) {
		logger.info("Welcome home!");

        PostMessageForm form =  new PostMessageForm();
        User user = securityService.getCurrentUser();
        if(user!=null && id!=null) {
        	jason.app.weixin.social.model.Message message = socialService.getMessage(user.getId(), id);
        	if(message!=null) {
        		form.setId(message.getId());
        		form.setTitle(message.getTitle());
        		form.setContent(message.getContent());
        		if(message.getCategory()!=null) {
        			form.setCategory(message.getCategory().getId());
        		}
        		form.setDistance(message.getDistance());
        		form.setMaxAge(message.getMaxAge());
        		form.setMinAge(message.getMinAge());
        		form.setRating(message.getRating());
        		form.setSex(message.getSex());
        		form.setStatus(message.getStatus());
        	
        	}
        }
        if(form.getRating()==null) {
        	form.setRating(0F);
        }
        if(form.getMinAge()==null) {
        	form.setMinAge(0);
        }
        if(form.getMaxAge()==null) {
        	form.setMaxAge(100);
        }
        model.addAttribute("postMessageForm", form);
        model.addAttribute("categories", categoryService.findByParent("message.type", null));


		return "circle.post";
	}
	
    /**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @Transactional
    public String postSignUp(HttpServletRequest request,HttpServletResponse response,PostMessageForm postMessageForm, BindingResult result) {
        logger.info("Welcome home!");
        validator.validate(postMessageForm, result);
        User user = securityService.getCurrentUser();
        
        if(user==null) {
        	result.reject("title","the user is not valid, please login again!");
        }
        if(result.hasErrors()) {
        	 return "circle.post";
        }
        SocialUser profile = socialService.loadProfile(user.getId());
        Message form = new Message();
		form.setId(postMessageForm.getId());
		form.setTitle(postMessageForm.getTitle());
		form.setContent(postMessageForm.getContent());
		form.setCategory(categoryService.findById(postMessageForm.getCategory()));
		if(form.getDistance()!=null && form.getDistance()>0) {
			form.setDistance(postMessageForm.getDistance());
		}
		if(0==postMessageForm.getMinAge() ) {
			form.setMinAge(null);			
		}else {
			form.setMinAge(postMessageForm.getMinAge());
		}
		if(100==postMessageForm.getMaxAge()) {
			form.setMaxAge(null);
		}else {
			form.setMaxAge(postMessageForm.getMaxAge());
		}
		
		if(postMessageForm.getRating()!=null && postMessageForm.getRating()>0) {
			form.setRating(postMessageForm.getRating());
		}
		if(form.getSex()!=null && form.getSex()>0) {
			form.setSex(postMessageForm.getSex());
		}
		form.setStatus(postMessageForm.getStatus());
		form.setAuthor(profile);

		
		form = socialService.saveMessage(form);
		if(form.getStatus()==Status.PUBLISHED) {
			// publish the message
        	logger.info("publish message "+form.getId());
			SocialMessage msg = socialService.publishMessageToUser(form.getId(),profile.getId());
			
			final PublishMessageCommand command = new PublishMessageCommand(form.getId());
			jmsTemplate.send(new MessageCreator() {
	            public javax.jms.Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");  
	            	return session.createObjectMessage(command);
	              }
	          });
			
			return "redirect:/circle/message.do?id="+msg.getId();
		}
        return "redirect:/circle/post.do?id="+form.getId();
        
    }
    
    
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public @ResponseBody List<Message> messages(@PageableDefault(size=10,page=0) Pageable pageable) {
        User user = securityService.getCurrentUser();       
		List<Message> messages = socialService.getUserMessages(user.getId(),pageable);
		for(Message msg:messages) {
			if(msg.getCategory()!=null) {
				msg.setCategory(categoryService.findById(msg.getCategory().getId()));
			}
		}
		return messages;
	}  
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model,@PageableDefault(size=10,page=0,sort={"message.lastUpdate"},direction=Direction.DESC) Pageable pageable,@RequestParam(value="category",required=false) Long category) {

		User user = securityService.getCurrentUser();
		model.addAttribute("cate",category);
		List<Category> categories = categoryService.findByParent("message.type", null);
		model.addAttribute("categories", categories);
		return "circle.home";
	}
	
	@RequestMapping(value = "/messages2", method = RequestMethod.GET)
	public @ResponseBody List<Message> messages(@PageableDefault(size=10,page=0) Pageable pageable,@RequestParam(value="category",required=false) Long category) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		if(category!=null && category==-1) {
			messages  = socialService.getUserMessages(user.getId(),pageable);
		}else {
			messages  = socialService.getPersonalMessages(user.getId(),category,pageable);
		}
		// return socialService.getPersonalMessages(user.getId(),category,pageable);
		return messages;

	}
	
}
