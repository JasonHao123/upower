package jason.app.weixin.web.controller.social;

import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialMail;
import jason.app.weixin.social.service.ISocialService;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/social")
public class ConversationController {
	
	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private ISocialService socialService;
	
    @RequestMapping("/conversations")
    public String mails() {
        return "social.mail.list";
    }
    
    @RequestMapping("/conversation")
    public String conversation(Model model,@RequestParam("id") Long id) {   
    	model.addAttribute("id",id);
        return "social.conversation";
    }
    
	@RequestMapping(value = "/conversation2", method = RequestMethod.GET)
	public @ResponseBody List<SocialMail> requests2(@RequestParam("id") Long id,@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();

		return socialService.getUserConversation(Arrays.asList(user.getId(),id),pageable);

	}
}
