package jason.app.weixin.web.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String home1(Model model) {
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "settings.social";
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
		return "settings.social";
	}
}
