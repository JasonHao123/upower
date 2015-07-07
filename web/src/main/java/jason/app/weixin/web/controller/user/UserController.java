package jason.app.weixin.web.controller.user;

import jason.app.weixin.web.controller.user.model.ProfileForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
	  private static final Logger logger = LoggerFactory
	            .getLogger(UserController.class);

    @RequestMapping("/index")
    public String home() {
        return "user.home";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "user.profile";
    }
    
    @RequestMapping("/follow")
    public String follow() {
        return "user.profile";
    }
    
    @RequestMapping(value="/profile/edit",method=RequestMethod.GET)
    public String editProfile(Model model) {
        ProfileForm form =  new ProfileForm();
        model.addAttribute("form", form);
        return "user.profile.edit";
    }
    
}
