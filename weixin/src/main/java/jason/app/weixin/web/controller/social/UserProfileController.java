package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.social.model.ProfileForm;
import jason.app.weixin.web.controller.social.model.ProfileValidator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/social")
public class UserProfileController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(UserProfileController.class);
    
@Autowired
private JmsTemplate jmsTemplate;
	
@Autowired
private ICategoryService categoryService;

@Autowired
private ISocialService socialService;

@Autowired
private ISecurityService securityService;

private ProfileValidator validator = new ProfileValidator();
    @RequestMapping(value="/profile/edit",method=RequestMethod.GET)
    public String editProfile(Model model) {
        ProfileForm form =  new ProfileForm();
        User user = securityService.getCurrentUser();
        if(user!=null) {
        	SocialUser profile = socialService.loadProfile(user.getId());
        	if(profile!=null) {
        		form.setId(profile.getId());
        		form.setNickname(profile.getNickname());
        		form.setAge(profile.getAge());
        		form.setCategory1(profile.getCategory1());
        		form.setCategory2(profile.getCategory2());
        		form.setLocation(profile.getLocation());
        		form.setHobby(profile.getHobby());
        	}
        }
        model.addAttribute("profileForm", form);
        model.addAttribute("categories", categoryService.findByParent("job.category", null));
        if(form.getCategory1()!=null) {
            model.addAttribute("categories2", categoryService.findByParent("job.category", form.getCategory1()));
        }
        return "social.profile.edit";
    }
    
    /**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    @Transactional
    public String postSignUp(HttpServletRequest request,HttpServletResponse response,ProfileForm profileForm, BindingResult result) {
        logger.info("Welcome home!");
        validator .validate(profileForm, result);
        User user = securityService.getCurrentUser();
        if(user==null) {
        	result.reject("nickname","the user is not valid, please login again!");
        }
        if(result.hasErrors()) {
        	 return "user.profile.edit";
        }
        final SocialUser profile = new SocialUser();
        profile.setId(user.getId());
        profile.setNickname(profileForm.getNickname());
        profile.setAge(profileForm.getAge());
        profile.setCategory1(profileForm.getCategory1());
        profile.setCategory2(profileForm.getCategory2());
        profile.setHobby(profileForm.getHobby());
        profile.setLocation(profileForm.getLocation());
        socialService.saveProfile(profile);
        
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              //  return session.createTextMessage("hello queue world");
            	CreateUserCommand addUser = new CreateUserCommand();
            	addUser.setUserId(profile.getId());
            	return session.createObjectMessage(addUser);
              }
          });
   
    
        	return "redirect:/social/home.do";
        
    }

}
