package jason.app.weixin.web.controller.settings;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.settings.model.SettingsForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private ISecurityService service;

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String home1(Model model) {
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "settings.social";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String settings(Model model) {
		model.addAttribute("friendshipTypes",categoryService.findByParent("friendship.type", null));
		List<Category> categories = categoryService.findByParent("message.type", null);
		model.addAttribute("categories", categories);
		model.addAttribute("profileVisibility", categoryService.findByParent("profile.visibility", null));
		User user = service.getCurrentUser();
		Settings settings = socialService.getUserSettings(user.getId());
		SettingsForm form = new SettingsForm();
		form.setProfileVisibility(settings.getProfileVisibility());
		form.setPersonalCircal(settings.getPersonalCircal());
		form.setGroupCircle(settings.getGroupCircle());
		form.setAcceptMessageTypes(settings.getAcceptMessageTypes());
		model.addAttribute("settingsForm",form);
		return "settings";
	}
	
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String postSettings(Model model,final SettingsForm settingsForm, BindingResult result) {
		Settings settings = new Settings();
		User user = service.getCurrentUser();
		settings.setId(user.getId());
		settings.setProfileVisibility(settingsForm.getProfileVisibility());
		settings.setPersonalCircal(settingsForm.getPersonalCircal());
		settings.setGroupCircle(settingsForm.getGroupCircle());
		settings.setAcceptMessageTypes(settingsForm.getAcceptMessageTypes());
		socialService.saveSettings(settings);
		return "redirect:/settings/index.do";
	}
	
	@RequestMapping(value = "/social", method = RequestMethod.GET)
	public String home2(Model model) {
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "settings.social";
	}
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String home3(Model model) {
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "settings.message";
	}
}
