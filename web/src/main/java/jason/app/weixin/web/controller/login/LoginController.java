package jason.app.weixin.web.controller.login;

import jason.app.weixin.web.service.IUserAccountValidationService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private IUserAccountValidationService service;
    /**
     * The public index page, used for unauthenticated users.
     */
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String displayLogin(HttpServletRequest request) {
    	String agent =  request.getHeader("User-Agent");
    	if(agent!=null && agent.indexOf("MicroMessenger")>=0) {
    		return "redirect:/weixin/oauth/authorize";
    	}
       return "login";
    }
    
    @RequestMapping(value="/check", method=RequestMethod.GET)
    public String performCheck() {
       String result = service.validate();
       if(result==null) {
    	   result = "redirect:/user/index.do";
       }
       return result;
    }
}
