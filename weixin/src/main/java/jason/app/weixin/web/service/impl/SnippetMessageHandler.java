package jason.app.weixin.web.service.impl;

import jason.app.weixin.common.model.SnippetMessageCommand;
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

@Component
public class SnippetMessageHandler extends MessageHandler {
	private static Logger logger = LoggerFactory.getLogger(SnippetMessageHandler.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return super.canHandle(params, header) && params.getContent().length()>8;
	}
	
	@Override
	public WeixinParam handle(final WeixinParam params, WeixinHeader header) {
		
		final SnippetMessageCommand command = new SnippetMessageCommand();
		command.setOpenId(params.getFromUserName());

		
			command.setContent(params.getContent().trim());
		
			jmsTemplate.send(new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");  
	            	logger.info("save image from "+params.getFromUserName());
	            	return session.createObjectMessage(command);
	              }
	        });
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("文本保存成功,回复添加标签，以空格分隔多个标签");
        return response;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+98;
	}
}
