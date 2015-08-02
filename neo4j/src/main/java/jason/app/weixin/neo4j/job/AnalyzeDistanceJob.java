package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserNeo4jRepository;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialRelationDTO;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialUserRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


public class AnalyzeDistanceJob {
	
	@Autowired
	private SocialUserRepository userRepo;
	@Autowired
	private SocialUserNeo4jRepository userRepo2;		
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Autowired
	private SocialDistanceRepository distanceRepo;
	
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次   
    @Transactional()
    public void myTest(){  
		
    	List<SocialUserImpl> users2 =  userRepo.findAll();
    	for(SocialUserImpl usr:users2) {

    			SocialUser user = userRepo2.findByUserId(usr.getId());
    			String query = "start one=node("+user.getId()+")  MATCH p = shortestPath(one-[:RELATE_TO*.."+7+"]->(two:SocialUser))  RETURN distinct one.userId as from,two.userId as to,length(p) as distance";
    			QueryResultBuilder users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
    			Iterator<Map> items = users.as(Map.class).iterator();
    			
    			while(items.hasNext()) {
    				 Map item = (Map) items.next();
    				 final SocialRelationDTO dto = new SocialRelationDTO();
    		
    					try {
							BeanUtils.copyProperties(dto,item);
						} catch (Exception e) {
			
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(dto.getFrom(), dto.getTo());
    					if(distance==null) {
    						distance = new SocialDistanceImpl();
    						distance.setFromUser(userRepo.findOne(dto.getFrom()));
    						distance.setToUser(userRepo.findOne(dto.getTo()));
    					}
    					distance.setDistance(dto.getDistance());
    					distanceRepo.save(distance);
    			}
    	}
    }  
}
