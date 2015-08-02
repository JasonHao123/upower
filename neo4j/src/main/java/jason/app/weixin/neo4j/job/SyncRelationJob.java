package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.util.ArrayUtil;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


public class SyncRelationJob {
	
	@Autowired
	private SocialRelationshipRepository userRepo;
	
	
	@Autowired
	private INeo4jService neo4jService;
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    @Transactional
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
    	List<SocialRelationshipImpl> users =  userRepo.findByLastUpdateGreaterThan(calendar.getTime());
    	for(SocialRelationshipImpl user:users) {
    		neo4jService.createRelation(user.getFrom().getId(),user.getTo().getId(),ArrayUtil.toLongArray(user.getRelationType()),user.getRating());
    	}
    }  
}
