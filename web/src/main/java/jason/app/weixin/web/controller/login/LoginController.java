package jason.app.weixin.web.controller.login;

import jason.app.weixin.web.service.IUserAccountValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private IUserAccountValidationService service;
    /**
     * The public index page, used for unauthenticated users.
     */
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView displayLogin() {
       return new ModelAndView("login");
    }
    
    @RequestMapping(value="/check", method=RequestMethod.GET)
    public String performCheck() {
       String result = service.validate();
       if(result==null) {
    	   result = "redirect:/index.do";
       }
       return result;
    }
}
