package jason.app.weixin.web.controller.login;

import jason.app.weixin.security.exception.UserAlreadyExistException;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.web.controller.login.model.SignupForm;
import jason.app.weixin.web.controller.login.validator.SignupValidator;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {
    
    private final Validator validator = new SignupValidator();
    
    @Autowired(required=false)
    private ISecurityService facade;
    
    @Autowired(required=false)
    private UserDetailsService userDetailsService;
    
    private static final Logger logger = LoggerFactory
            .getLogger(SignUpController.class);
  //  @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String signupStep1() {
        return "signup";
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public ModelAndView signupUser(HttpSession session) {

       SignupForm User =  new SignupForm();
      
       ModelAndView model = new ModelAndView("signup.user", "signupForm", User);
     
       return model;
    }
    
    @RequestMapping(value="/signupagent", method=RequestMethod.GET)
    public ModelAndView signupAgent(HttpSession session) {

       SignupForm User =  new SignupForm();
      
       ModelAndView model = new ModelAndView("signup.agent", "signupForm", User);
     
       return model;
    }
    
    @RequestMapping(value="/signupcompany", method=RequestMethod.GET)
    public ModelAndView signupCompany(HttpSession session) {

       SignupForm User =  new SignupForm();
      
       ModelAndView model = new ModelAndView("signup.company", "signupForm", User);
     
       return model;
    }

    @RequestMapping(value = "/signupagent", method = RequestMethod.POST)
    @Transactional
    public String postSignUpAgent(HttpServletRequest request,HttpServletResponse response,HttpSession session,SignupForm signupForm, BindingResult result) {
        String result2 = postSignUp(request,response,session,signupForm,result,"ROLE_AGENT");
        if(result2==null) {
            result2 = "signup.agent";
        }
        return result2;
        
    }
    
    @RequestMapping(value = "/signupcompany", method = RequestMethod.POST)
    @Transactional
    public String postSignUpCompany(HttpServletRequest request,HttpServletResponse response,HttpSession session,SignupForm signupForm, BindingResult result) {
        String result2 = postSignUp(request,response,session,signupForm,result,"ROLE_COMPANY");
        if(result2==null) {
            result2 = "signup.agent";
        }
        return result2;
    }
    
    /**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @Transactional
    public String postSignUpUser(HttpServletRequest request,HttpServletResponse response,HttpSession session,SignupForm signupForm, BindingResult result) {
        String result2 = postSignUp(request,response,session,signupForm,result,"ROLE_USER");
        if(result2==null) {
            result2 = "signup.user";
        }
        return result2;
    }
    private String postSignUp(HttpServletRequest request,HttpServletResponse response,HttpSession session,SignupForm signupForm, BindingResult result,String role) {
        logger.info("Welcome home!");
        validator.validate(signupForm, result);
        if (result.hasErrors()) {
            signupForm.setPassword("");
            signupForm.setPasswordAgain("");         
            return null;
        }

        try {
            userDetailsService.loadUserByUsername(signupForm.getUsername());
            result.rejectValue("username", "validate.err.username", "User is already exist!");
            signupForm.setPassword("");
            signupForm.setPasswordAgain("");         
            return null;
        }catch(UsernameNotFoundException e) {

            try {
                facade.createUser(signupForm.getUsername(), signupForm.getPassword(), Arrays.asList(new String[]{"ROLE_USER",role}));
                // password encoded in signup, need to reset again
                //user.setPassword(signupForm.getPassword());
                facade.login(request,response,signupForm.getUsername(), signupForm.getPassword());
            } catch (UserAlreadyExistException ee) {
                result.rejectValue("username", "validate.err.username", "User is already exist!");
                signupForm.setPassword("");
                signupForm.setPasswordAgain("");         
                return null;
            }

        }
        
        return "redirect:/check.do";
    }
}
