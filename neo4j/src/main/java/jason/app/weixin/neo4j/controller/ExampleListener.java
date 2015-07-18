package jason.app.weixin.neo4j.controller;

import jason.app.weixin.social.api.command.ICommand;
import jason.app.weixin.social.api.command.ICommandReceiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ExampleListener implements MessageListener {
	
	@Autowired
	private ICommandReceiver receiver;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ExampleListener.class);
	
	@Transactional
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }else if(message instanceof ObjectMessage) {
        	try {
				ICommand command = (ICommand) ((ObjectMessage)message).getObject();
				command.setReceiver(receiver);
				command.execute();
				logger.info(command.toString());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
