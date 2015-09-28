package jason.app.weixin.web.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import jason.app.weixin.common.model.LinkMessageCommand;
import jason.app.weixin.common.model.TagMessageCommand;
import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class TagMessageHandler extends MessageHandler {
	private static Logger logger = LoggerFactory.getLogger(TagMessageHandler.class);
	private static final String TAG_PATTERN = "^(\\S{1,20}\\s{1,3}){0,8}\\S{1,20}\\s{0,3}$";
	@Autowired
	private JmsTemplate jmsTemplate;
	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return super.canHandle(params, header) && params.getContent().matches(TAG_PATTERN);
	}
	
	@Override
	public WeixinParam handle(final WeixinParam params, WeixinHeader header) {
		
		final TagMessageCommand command = new TagMessageCommand();
		command.setOpenId(params.getFromUserName());
			command.setTags(params.getContent().trim());

			jmsTemplate.send(new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");  
	            	logger.info("save tags from "+params.getFromUserName());
	            	return session.createObjectMessage(command);
	              }
	        });
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("文本保存成功！");
        return response;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+98;
	}
}
