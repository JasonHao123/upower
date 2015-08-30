package jason.app.weixin.web.controller;

import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.social.service.ISocialService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ISocialService socialService;
	
	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "home";
	}
	
	@RequestMapping(value = "/jms", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody String jms(Model model,@RequestParam("id") Long id,@RequestParam("openid") String openid) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		final CreateUserCommand command = new CreateUserCommand(id, openid);
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              //  return session.createTextMessage("hello queue world");  
            	logger.info("send create user command");
            	return session.createObjectMessage(command);
              }
          });
		return "home";
	}
	
	@RequestMapping(value = "/public/result", method = RequestMethod.GET)
	public String publicResult(Model model) {
		logger.info("Welcome home!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller!");
		return "public.result";
	}
}
