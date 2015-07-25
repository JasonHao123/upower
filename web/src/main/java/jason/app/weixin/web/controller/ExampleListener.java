package jason.app.weixin.web.controller;

import jason.app.weixin.social.api.model.SocialRelationDTO;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialUserRepository;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ExampleListener implements MessageListener {
	
	@Autowired
	private SocialDistanceRepository distanceRepo;
	
	@Autowired
	private SocialUserRepository userRepo;

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
        	ObjectMessage hmsg = (ObjectMessage)message;
        	try {
				System.out.println(hmsg.getObject());
				SocialRelationDTO dto = (SocialRelationDTO)hmsg.getObject();
				if(dto.getFrom()!=null && dto.getTo()!=null) {
					SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(dto.getFrom(), dto.getTo());
					if(distance==null) {
						distance = new SocialDistanceImpl();
						distance.setFromUser(userRepo.findOne(dto.getFrom()));
						distance.setToUser(userRepo.findOne(dto.getTo()));
					}
					distance.setDistance(dto.getDistance());
					distanceRepo.save(distance);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
