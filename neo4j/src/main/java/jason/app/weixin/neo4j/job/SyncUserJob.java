package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.util.ArrayUtil;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


public class SyncUserJob {
	
	@Autowired
	private SocialUserRepository userRepo;
	
	
	@Autowired
	private INeo4jService neo4jService;
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    @Transactional
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
    	List<SocialUserImpl> users =  userRepo.findByLastUpdateGreaterThan(calendar.getTime());
    	for(SocialUserImpl user:users) {
    		neo4jService.createUser(user.getId(), user.getNickname(), user.getAge(), user.getCategory1(), user.getCategory2(), ArrayUtil.toArray(user.getLocations()), ArrayUtil.toArray(user.getHobbys()));
    	}
    }  
}
