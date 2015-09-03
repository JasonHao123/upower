package jason.app.weixin.web.controller.circle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/circle")
public class CircleController {

	private static final Logger logger = LoggerFactory
			.getLogger(CircleController.class);
	
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "circle.post";
	}
	
	
}
