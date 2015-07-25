package jason.app.weixin.web.service.impl;

import jason.app.weixin.social.api.command.AnalyzeUserRelationCommand;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class AnalyzeUserRelationJob {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private SocialUserRepository userRepo;
	
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    public void myTest(){  
    	List<SocialUserImpl> users =  userRepo.findAll();
    	for(SocialUserImpl user:users) {
    		final Long id = user.getId();
			jmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                  //  return session.createTextMessage("hello queue world");
                	AnalyzeUserRelationCommand command = new AnalyzeUserRelationCommand();
                	command.setUserId(id);
                	command.setExtensiveLevel(3);
                	return session.createObjectMessage(command);
                  }
              });
    	}
    }  
}
