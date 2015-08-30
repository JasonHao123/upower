package jason.app.weixin.web.controller.social;

import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Text;
import jason.app.weixin.common.util.CommonUtil;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.SocialMail;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import java.util.Arrays;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/social")
public class ConversationController {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private ISocialService socialService;
	
    @RequestMapping("/conversations")
    public String mails() {
        return "social.mail.list";
    }
    
    @RequestMapping("/conversation")
    public String conversation(Model model,@RequestParam("id") Long id) {   
    	model.addAttribute("id",id);
        return "social.conversation";
    }
    
	@RequestMapping(value = "/conversation2", method = RequestMethod.GET)
	public @ResponseBody List<SocialMail> requests2(@RequestParam("id") Long id,@PageableDefault(size=10,page=0) Pageable pageable) {

		User user = securityService.getCurrentUser();

		List<SocialMail> mails =  socialService.getUserConversation(Arrays.asList(user.getId(),id),pageable);
		for(SocialMail mail:mails) {
			if(mail.getFrom().getId().equals(user.getId())) {
				mail.setSelf(true);
			}
		}
		return mails;

	}
	
	
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody SocialMail mail(@RequestParam("id") Long id,@RequestParam("message") final String message) {
		SocialMail comment = new SocialMail();
		User user = securityService.getCurrentUser();
		final SocialUser from = socialService.loadProfile(user.getId());
		final SocialUser to = socialService.loadProfile(id);
		comment.setFrom(from);
		comment.setTo(to);
		comment.setSelf(true);
		comment.setMessage(message);
		comment = socialService.saveMail(comment);

		if(StringUtils.hasText(to.getOpenid())) {
			jmsTemplate.send(new MessageCreator() {
	            public javax.jms.Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");
	            	SendMessageCommand command = new SendMessageCommand();
	            	command.setMsgtype("text");
	            	command.setTouser(to.getOpenid());
	            	command.setText(new Text(from.getNickname() +": "+message+" <a href=\"http://www.weaktie.cn/weixin/social/conversation.do?id="+from.getId()+"\">聊天记录</a>"));
	            	return session.createObjectMessage(command);
	              }
	          });
		}
		return comment;
	} 
}
