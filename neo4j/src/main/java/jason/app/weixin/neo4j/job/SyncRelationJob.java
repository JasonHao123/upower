package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.domain.SocialRelation;
import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserNeo4jRepository;
import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.util.ArrayUtil;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SyncRelationJob {
	
	@Autowired
	private SocialRelationshipRepository relationRepo;
	
	@Autowired
	private SocialUserNeo4jRepository userRepo;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;

	
    @Scheduled(cron="0/10 * *  * * ? ")   //每5秒执行一次   
    @Transactional
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -10);
		
    	List<SocialRelationshipImpl> relations =  relationRepo.findByLastUpdateGreaterThan(calendar.getTime());
    	for(SocialRelationshipImpl rel:relations) {
    		SocialUser user = userRepo.findByUserId(rel.getFrom().getId());
    		SocialUser user2 = userRepo.findByUserId(rel.getTo().getId());
    		

    /**		
    		user.addFriend(user2, types);
    		userRepo.save(user);
    		*/
    		// cannot create duplicate relation using repository, use neo4jtemplate instead
    		// check whether the relation exists
    		
    		SocialRelation relation = userRepo.findByFromAndTo(user.getId(),user2.getId());
    		if(relation==null) {
    			relation = neo4jTemplate.createRelationshipBetween(user, user2, SocialRelation.class, "RELATE_TO", true);
    			relation.setFrom(user);
    			relation.setTo(user2);
    		}
    		relation.setTypes(ArrayUtil.toLongArray(rel.getRelationType()));
    		relation.setRating(rel.getRating());
    		neo4jTemplate.save(relation);
    		//neo4jService.createRelation(user.getFrom().getId(),user.getTo().getId(),ArrayUtil.toLongArray(user.getRelationType()),user.getRating());
    	}
    }  
}
