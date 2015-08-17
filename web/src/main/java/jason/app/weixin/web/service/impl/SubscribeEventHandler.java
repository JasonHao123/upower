package jason.app.weixin.web.service.impl;

import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class SubscribeEventHandler extends EventHandler {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ISecurityService securityService;
	
	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return "subscribe".equals(params.getEvent());
	}
	
	@Override
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		// register a new user, or enable the user if already exists
		String openid = params.getFromUserName();
		User user = securityService.findExternalUser(openid);
		if(user==null) {
			user = securityService.createExternalUser(openid, openid, Arrays.asList(new String[]{"ROLE_USER"}));
		}else {
			securityService.enableUser(user.getId(),true);
		}
		final CreateUserCommand command = new CreateUserCommand(user.getId(), openid);
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              //  return session.createTextMessage("hello queue world");    
            	return session.createObjectMessage(command);
              }
          });
		// TODO Auto-generated method stub
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("欢迎使用友势力社交平台！");
        return response;
	}
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+1;
	}

}
