package jason.app.weixin.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    /**
     * The public index page, used for unauthenticated users.
     */
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String displayLogin(HttpServletRequest request) {
    	String agent =  request.getHeader("User-Agent");
    	if(agent!=null && agent.indexOf("MicroMessenger")>=0) {
    		return "redirect:/oauth/authorize.do";
    	}
       return "login";
    }

}
