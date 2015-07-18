package jason.app.weixin.web.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	@Autowired
	private JmsTemplate jmsTemplate;
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "index";
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public @ResponseBody String send() {
		logger.info("Welcome home!");
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello queue world");
              }
          });
		return "index";
	}
	

}
