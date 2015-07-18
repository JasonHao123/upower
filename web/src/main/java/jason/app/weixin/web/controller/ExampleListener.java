package jason.app.weixin.web.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.hornetq.jms.client.HornetQMessage;

public class ExampleListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }else if(message instanceof HornetQMessage) {
        	HornetQMessage hmsg = (HornetQMessage)message;
        	System.out.println(hmsg.getJMSMessageID());
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
