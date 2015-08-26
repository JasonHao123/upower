package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/social")
public class SocialController {
	private static final Logger logger = LoggerFactory
			.getLogger(SocialController.class);

	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ISecurityService securityService;
	
	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "social.home";
	}
	
    @RequestMapping("/friends")
    public String friends() {
        return "social.friend.list";
    }
    
	@RequestMapping(value = "/friends2", method = RequestMethod.GET)
	public @ResponseBody List<SocialUser> messages(@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		
		return socialService.getFriends(user.getId(),pageable);

	}
	
    @RequestMapping("/mails")
    public String mails() {
        return "social.mail.list";
    }
    
    
}
