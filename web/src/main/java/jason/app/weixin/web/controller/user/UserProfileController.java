package jason.app.weixin.web.controller.user;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.user.model.ProfileForm;
import jason.app.weixin.web.controller.user.validator.ProfileValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserProfileController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(UserProfileController.class);
    
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
        return "user.profile.edit";
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
        SocialUser profile = new SocialUser();
        profile.setId(user.getId());
        profile.setNickname(profileForm.getNickname());
        profile.setAge(profileForm.getAge());
        socialService.saveProfile(profile);
        if(profileForm.getId()==null) {
        	return "redirect:/user/index.do";
        }else {
        	return "redirect:/user/profile.do";
        }
        
    }
    @RequestMapping("/profile")
    public String profile() {
        return "user.profile";
    }
}
