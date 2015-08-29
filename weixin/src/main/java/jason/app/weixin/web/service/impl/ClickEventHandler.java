package jason.app.weixin.web.service.impl;

import jason.app.weixin.common.model.AnalyzeRelationCommand;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClickEventHandler extends EventHandler {
	private static Logger logger = LoggerFactory.getLogger(ClickEventHandler.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ISecurityService securityService;
	
	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return "CLICK".equals(params.getEvent());
	}
	
	@Override
	@Transactional
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		// register a new user, or enable the user if already exists
		String openid = params.getFromUserName();
		logger.info("openid:"+openid);
		/**
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
            	logger.info("send create user command");
            	return session.createObjectMessage(command);
              }
          });
          */
		// TODO Auto-generated method stub
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());  
        String content = "No valid event key";
        if("POWER".equals(params.getEventKey())) {
        	content ="社交关系分析中，请稍候！";
        	final AnalyzeRelationCommand command = new AnalyzeRelationCommand(openid);
        	jmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                  //  return session.createTextMessage("hello queue world");  
                	logger.info("send create user command");
                	return session.createObjectMessage(command);
                  }
              });
        }else if("SERVICE".equals(params.getEventKey())) {
        	content = "服务区建设中，敬请期待！";
        }
        response.setContent(content);
        return response;
	}
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+1;
	}

}
