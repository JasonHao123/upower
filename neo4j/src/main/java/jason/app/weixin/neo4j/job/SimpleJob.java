package jason.app.weixin.neo4j.job;

import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


public class SimpleJob {
	
	@Autowired
	private SocialUserRepository userRepo;
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    @Transactional()
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
    	List<SocialUserImpl> users =  userRepo.findAll();
    	for(SocialUserImpl user:users) {
    		user.setLastUpdate(new Date());
    		userRepo.save(user);
    	}
    }  
}
