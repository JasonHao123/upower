package jason.app.weixin.neo4j.service.impl;

import jason.app.weixin.neo4j.domain.SocialRelation;
import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserNeo4jRepository;
import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.model.SocialRelationDTO;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Neo4jServiceImpl implements INeo4jService{
	
	private static final Logger logger = LoggerFactory
			.getLogger(Neo4jServiceImpl.class);
	
	@Autowired
	private ISocialService socialService;


	@Autowired
	private SocialUserNeo4jRepository userRepo;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Override
	@Transactional
	public void createUser(Long userId, String nickname, Integer age,Long category1,Long category2,String[] locations,String[] hobbys) {
		// TODO Auto-generated method stub
		SocialUser user = userRepo.findByUserId(userId);
		if(user==null) {
			user = new SocialUser();
		}
		user.setName(nickname);
		user.setUserId(userId);
		user.setAge(age);
		user.setCategory1(category1);
		user.setCategory2(category2);
		user.setHobbys(hobbys);
		user.setLocations(locations);
		userRepo.save(user);
	}

	@Override
	public void createRelation(Long fromUser, Long toUser, Long[] types,Float rating) {
		// TODO Auto-generated method stub
		SocialUser user = userRepo.findByUserId(fromUser);
		SocialUser user2 = userRepo.findByUserId(toUser);
		

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
		relation.setTypes(types);
		relation.setRating(rating);
		neo4jTemplate.save(relation);
	}

	@Override
	@Transactional
	public void analyzeUserRelationDistance(Long userId, Integer extensiveLevel) {
		// TODO Auto-generated method stub
		try {
		SocialUser user = userRepo.findByUserId(userId);
		String query = "start one=node("+user.getId()+")  MATCH p = shortestPath(one-[:RELATE_TO*.."+extensiveLevel+"]->(two:SocialUser))  RETURN distinct one.userId as from,two.userId as to,length(p) as distance";
		QueryResultBuilder users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
		Iterator<Map> items = users.as(Map.class).iterator();
		
		while(items.hasNext()) {
			 Map item = (Map) items.next();
			 final SocialRelationDTO dto = new SocialRelationDTO();
	
				BeanUtils.copyProperties(dto,item);
				socialService.saveDistance(dto);
		}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createUser(jason.app.weixin.social.model.SocialUser user2) {
		// TODO Auto-generated method stub
		SocialUser user = userRepo.findByUserId(user2.getId());
		if(user==null) {
			user = new SocialUser();
		}
		user.setName(user2.getNickname());
		user.setUserId(user2.getId());
		user.setAge(user2.getAge());
		user.setCategory1(user2.getCategory1());
		user.setCategory2(user2.getCategory2());
		user.setHobbys(user2.getHobby());
		user.setLocations(user2.getLocation());
		user.setSex(user2.getSex());
		user.setCountry(user2.getCountry());
		user.setProvince(user2.getProvince());
		user.setCity(user2.getCity());
		userRepo.save(user);
	}


}
