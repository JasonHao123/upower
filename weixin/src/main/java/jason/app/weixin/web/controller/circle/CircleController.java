package jason.app.weixin.web.controller.circle;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.constant.Status;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.circle.model.PostMessageForm;
import jason.app.weixin.web.controller.circle.model.PostMessageValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jms.core.JmsTemplate;
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
		form.setDistance(postMessageForm.getDistance());
		form.setMaxAge(postMessageForm.getMaxAge());
		form.setMinAge(postMessageForm.getMinAge());
		form.setRating(postMessageForm.getRating());
		form.setSex(postMessageForm.getSex());
		form.setStatus(postMessageForm.getStatus());
		form.setAuthor(profile);
		form = socialService.saveMessage(form);
		if(form.getStatus()==Status.PUBLISHED) {
			// publish the message
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
	
}
