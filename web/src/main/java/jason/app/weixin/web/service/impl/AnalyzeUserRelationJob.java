package jason.app.weixin.web.service.impl;

import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class AnalyzeUserRelationJob {

	
	@Autowired
	private SocialUserRepository userRepo;
	
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    public void myTest(){  
    	List<SocialUserImpl> users =  userRepo.findAll();
    	for(SocialUserImpl user:users) {
    		final Long id = user.getId();
			
    	}
    }  
}
