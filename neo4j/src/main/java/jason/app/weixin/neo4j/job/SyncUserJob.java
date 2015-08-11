package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserNeo4jRepository;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.util.ArrayUtil;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SyncUserJob {
	
	@Autowired
	private SocialUserRepository userRepo;

	@Autowired
	private SocialUserNeo4jRepository userRepo2;
	
    @Scheduled(cron="0/10 * *  * * ? ")   //每5秒执行一次   
    @Transactional
    public void myTest(){  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -10);
		
    	List<SocialUserImpl> users =  userRepo.findByLastUpdateGreaterThan(calendar.getTime());
    	for(SocialUserImpl usr:users) {
    		SocialUser user = userRepo2.findByUserId(usr.getId());
    		if(user==null) {
    			user = new SocialUser();
    		}
    		user.setName(usr.getNickname());
    		user.setUserId(usr.getId());
    		user.setAge(usr.getAge());
    		user.setCategory1(usr.getCategory1());
    		user.setCategory2(usr.getCategory2());
    		user.setHobbys(ArrayUtil.toArray(usr.getHobbys()));
    		user.setLocations(ArrayUtil.toArray(usr.getLocations()));
    		userRepo2.save(user);
    		//neo4jService.createUser(user.getId(), user.getNickname(), user.getAge(), user.getCategory1(), user.getCategory2(), ArrayUtil.toArray(user.getLocations()), ArrayUtil.toArray(user.getHobbys()));
    	}
    }  
}
