package jason.app.weixin.neo4j.service;

import jason.app.weixin.neo4j.domain.SocialRelation;
import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserRepository;
import jason.app.weixin.social.api.command.AddUserCommand;
import jason.app.weixin.social.api.command.ICommandReceiver;
import jason.app.weixin.social.api.model.SocialRelationDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandReceiver implements ICommandReceiver{
	
	private static final Logger logger = LoggerFactory
			.getLogger(CommandReceiver.class);

	@Autowired
	private SocialUserRepository userRepo;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
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
	public void createRelation(Long fromUser, Long toUser, Long[] types) {
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
		neo4jTemplate.save(relation);
	}

	@Override
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
			jmsTemplate.send(new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	              //  return session.createTextMessage("hello queue world");

	    			logger.info(dto.toString());
	            	return session.createObjectMessage(dto);
	              }
	          });
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}
