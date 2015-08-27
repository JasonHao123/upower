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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	

    @RequestMapping("/home")
    public String profile(Model model,@RequestParam(required=false,value="id") Long id) {
        User user = securityService.getCurrentUser();
        if(user==null) {
        	return "redirect:/social/profile/edit.do";
        }else {
        	SocialUser profile = null;
        	if(id==null || user.getId()==id) {
        		profile = socialService.loadProfile(user.getId());
        	}else {
        		profile = socialService.loadProfile(id);
        	}
        	model.addAttribute("profile",profile);
        	model.addAttribute("isSelf",StringUtils.isEmpty(id) || user.getId()==id);
    		Integer distance = socialService.getSocialDistance(user.getId(),profile.getId());
    		model.addAttribute("distance",distance);
    		model.addAttribute("isFriend",socialService.isFriend(user.getId(), profile.getId()));
            return "social.home";
        }
      /**  
    	if(id!=null) {
    		SocialUser profile = socialService.loadProfile(id);
        	model.addAttribute("profile",profile);
        	boolean self = user.getId()==profile.getId();
        	if(!self) {
        		Integer distance = socialService.getSocialDistance(user.getId(),profile.getId());
        		model.addAttribute("distance",distance);
        	}
        	model.addAttribute("isSelf",self);
    	}else {

	        if(user!=null) {
	        	SocialUser profile = socialService.loadProfile(user.getId());
	        	model.addAttribute("profile",profile);
	        	model.addAttribute("isSelf",user.getId()==profile.getId());
	        }else {
	        	return "redirect:/user/profile/edit.do";
	        }
    	}
        return "user.profile";
        
        */
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
    
}
