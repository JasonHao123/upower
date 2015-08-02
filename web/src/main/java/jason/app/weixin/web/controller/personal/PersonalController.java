package jason.app.weixin.web.controller.personal;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.service.ISocialService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/personal")
public class PersonalController {
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ISecurityService securityService;
	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model,@PageableDefault(size=10,page=0,sort={"message.lastUpdate"},direction=Direction.DESC) Pageable pageable,@RequestParam(value="category",required=false) Long category) {

		User user = securityService.getCurrentUser();
/**		List<Message> messages = null;
		if(category!=null && category==-1) {
			messages = socialService.getUserMessages(user.getId(),pageable);
		}else {
			messages  = socialService.getPersonalMessages(user.getId(),category,pageable);
		}
		model.addAttribute("messages",messages);
		*/
		model.addAttribute("cate",category);
		List<Category> categories = categoryService.findByParent("message.type", null);
		model.addAttribute("categories", categories);
		return "personal.home";
	}
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public @ResponseBody List<Message> messages(@PageableDefault(size=10,page=0) Pageable pageable,@RequestParam(value="category",required=false) Long category) {

		User user = securityService.getCurrentUser();
		List<Message> messages =null;
		if(category!=null && category==-1) {
			messages  = socialService.getUserMessages(user.getId(),pageable);
		}else {
			messages  = socialService.getPersonalMessages(user.getId(),category,pageable);
		}
		// return socialService.getPersonalMessages(user.getId(),category,pageable);
		return messages;

	}
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String post(Model model) {
		List<Category> categories = categoryService.findByParent("message.type", null);
		model.addAttribute("categories", categories);
		return "personal.post";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	@Transactional
	public String post2(Model model,@RequestParam("title") String title,@RequestParam("message") String message,@RequestParam("type") Long type) {
		User user = securityService.getCurrentUser();
		socialService.postPersonalMessage(user.getId(),title,message,type);
		return "personal.post";
	}
	
	
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String message(Model model,@RequestParam(value="id",required=false) Long id,@RequestParam(value="id2",required=false) Long id2) {
		User user = securityService.getCurrentUser();
		Message message = null;
		if(id!=null) {
			message = socialService.getMessage(user.getId(),id);
		}
		if(id2!=null) {
			message = socialService.getMessage2(user.getId(),id2); 
		}
		model.addAttribute("message", message);
		return "personal.message";
	}
}
