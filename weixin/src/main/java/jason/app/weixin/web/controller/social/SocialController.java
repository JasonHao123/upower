package jason.app.weixin.web.controller.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/social")
public class SocialController {
	private static final Logger logger = LoggerFactory
			.getLogger(SocialController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "social.home";
	}
}
