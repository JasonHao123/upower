package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.model.AnalyzeRelationCommand;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Text;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.Comment;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.social.model.PowerForm;
import jason.app.weixin.web.controller.social.model.PowerValidator;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/social")
public class SocialController {
	private static final Logger logger = LoggerFactory
			.getLogger(SocialController.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ISecurityService securityService;


	private PowerValidator validator = new PowerValidator();

	
    @RequestMapping("/friends")
    public String friends() {
        return "social.friend.list";
    }
    
	@RequestMapping(value = "/friends2", method = RequestMethod.GET)
	public @ResponseBody List<SocialUser> messages(@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();
	
		return socialService.getFriends(user.getId(),pageable);

	}    
	
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public @ResponseBody List<Comment> comments(@RequestParam("id") Long id,@PageableDefault(size=10,page=0) Pageable pageable) {

		return socialService.getUserComments(id,pageable);

	}  
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Comment comment(@RequestParam("id") Long id,@RequestParam("message") String message, @RequestParam("rating") Float rating) {
		Comment comment = new Comment();
		User user = securityService.getCurrentUser();
		comment.setAuthor(socialService.loadProfile(user.getId()));
		comment.setTarget(socialService.loadProfile(id));
		comment.setRating(rating);
		comment.setMessage(message);
		comment = socialService.saveComment(comment);
		return comment;
	}   
	
	
    @RequestMapping(value="/power", method = RequestMethod.GET)
    public String conversation(Model model) { 
    	PowerForm powerForm = new PowerForm();
    	powerForm.setDistance(1);
    	model.addAttribute("powerForm",powerForm);
        return "social.power";
    }
    
    @RequestMapping(value = "/power", method = RequestMethod.POST)
    @Transactional
    public String postSignUp(HttpServletRequest request,HttpServletResponse response,final PowerForm powerForm, BindingResult result) {
        logger.info("post power form!");
        validator.validate(powerForm, result);
        
        if(result.hasErrors()) {
        	 return "social.power";
        }
        User user = securityService.getCurrentUser();
        final SocialUser profile = socialService.loadProfile(user.getId());
		if(StringUtils.hasText(profile.getOpenid())) {
			logger.info("analyze "+profile.getOpenid()+" "+powerForm.getType()+" "+powerForm.getDistance());
			jmsTemplate.send(new MessageCreator() {
	            public javax.jms.Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");
	            	AnalyzeRelationCommand command = new AnalyzeRelationCommand();
	            	command.setOpenid(profile.getOpenid());
	            	command.setDistance(powerForm.getDistance());
	            	command.setType(powerForm.getType());
	            	return session.createObjectMessage(command);
	              }
	          });
		}else {
			logger.warn("user doesn't have openid ");
		}
        return "social.power.message";
    }
}
