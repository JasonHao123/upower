package jason.app.weixin.web.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	  private static final Logger logger = LoggerFactory
	            .getLogger(UserController.class);

    @RequestMapping("/index")
    public String home() {
        return "user.home";
    }


    
    @RequestMapping("/follow")
    public String follow() {
        return "user.profile";
    }
    

    
}
