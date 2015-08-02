package jason.app.weixin.neo4j.job;

import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialMessageImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.repository.MessageRepository;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialMessageRepository;
import jason.app.weixin.social.service.ISocialService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PublishMessageJob {
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private SocialDistanceRepository distanceRepo;
	
	@Autowired
	private SocialMessageRepository messageRepo2;

    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    @Transactional
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
    	List<MessageImpl> users =  messageRepo.findByLastUpdateGreaterThan(calendar.getTime());
    	for(MessageImpl user:users) {
    		Settings settings = socialService.getUserSettings(user.getAuthor().getId());
    		List<SocialDistanceImpl> targets =  distanceRepo.findByFromUser_IdAndDistanceLessThanEqual(user.getAuthor().getId(), settings.getPersonalCircal());
    		for(SocialDistanceImpl dis:targets) {
    			SocialUserImpl usr = dis.getToUser();
    			SocialMessageImpl msg = messageRepo2.findByUser_IdAndMessage_Id(usr.getId(),user.getId());
    			if(msg==null) {
    				msg = new SocialMessageImpl();
        			msg.setUser(usr);
        			
    			}
    			msg.setMessage(user);
    			messageRepo2.save(msg);
    			//SocialMessageImpl msg = new SocialMessageImpl();

    		}
    	}
    }  
}
