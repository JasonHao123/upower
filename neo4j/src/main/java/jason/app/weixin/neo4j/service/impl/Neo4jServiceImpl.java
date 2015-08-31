package jason.app.weixin.neo4j.service.impl;

import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.common.model.SeriesItem;
import jason.app.weixin.neo4j.domain.SocialRelation;
import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.neo4j.repository.SocialUserNeo4jRepository;
import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.service.ISocialService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

	@Override
	public AnalyzeResult analyzeSexAndAge(Long id, Integer distance) {
		AnalyzeResult result = new AnalyzeResult();
		List<SeriesItem> series = new ArrayList<SeriesItem>();
		try {
			SocialUser user = userRepo.findByUserId(id);
			String query = "START n=node("+user.getId()+") MATCH (n)-[r:RELATE_TO*.."+distance+"]->(m:SocialUser) WHERE m.userId<>"+id+"  WITH distinct m RETURN m.sex as sex,count(m) as cnt";
			QueryResultBuilder users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
			Iterator<Map> items = users.as(Map.class).iterator();	
			int i = 0;
			while(items.hasNext()) {
				 Map item = (Map) items.next();
				  SeriesItem dto = new SeriesItem();	
				  dto.setSeries("SEX");
				  dto.setKey(decodeSexKey((Integer) item.get("sex")));
				  dto.setValue(Double.valueOf(item.get("cnt").toString()));
				  dto.setOrder(i++);
				  series.add(dto);				  
			}

			query = "START n=node("+user.getId()+") MATCH (n)-[r:RELATE_TO*.."+distance+"]->(m:SocialUser) WHERE m.userId<>"+id+"  WITH distinct m RETURN m.age/10 as age,count(m) as cnt order by cnt desc, age asc";
			users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
			items = users.as(Map.class).iterator();	
			 i = 0;
			while(items.hasNext()) {
				 Map item = (Map) items.next();
				  SeriesItem dto = new SeriesItem();	
				  dto.setSeries("AGE");
				  dto.setKey(decodeAgeKey((Integer) item.get("age")));
				  dto.setValue(Double.valueOf(item.get("cnt").toString()));
				  dto.setOrder(i++);
				  series.add(dto);				  
			}
			result.setData(series);
		
		}catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	private String decodeAgeKey(Integer age) {
		// TODO Auto-generated method stub
		if(age==null) return "未知";
		else if(age==0) return "0-10岁";
		else if(age==1) return "10-20岁";
		else if(age==2) return "20-30岁";
		else if(age==3) return "30-40岁";
		else if(age==4) return "40-50岁";
		else if(age==5) return "50-60岁";
		else if(age==6) return "60-70岁";
		else if(age==7) return "70-80岁";
		else if(age==8) return "80-90岁";
		else if(age==9) return "90-100岁";
		else if(age==10) return "100-110岁";
		
		return "寿星";
	}

	private String decodeSexKey(Integer sex) {
		// TODO Auto-generated method stub
		if(sex==null) return "未知";
		else if(sex==1) return "男";
		else if(sex==2) return "女";
		return "超人";
	}

	@Override
	public AnalyzeResult analyzeLocation(Long id, Integer distance) {
		// TODO Auto-generated method stub
		AnalyzeResult result = new AnalyzeResult();
		List<SeriesItem> series = new ArrayList<SeriesItem>();
		try {
			SocialUser user = userRepo.findByUserId(id);
			String query = "START n=node("+user.getId()+") MATCH (n)-[r:RELATE_TO*.."+distance+"]->(m:SocialUser) WHERE m.userId<>"+id+"  WITH distinct m RETURN m.country as country,count(m) as cnt";
			QueryResultBuilder users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
			Iterator<Map> items = users.as(Map.class).iterator();	
			int i = 0;
			while(items.hasNext()) {
				 Map item = (Map) items.next();
				  SeriesItem dto = new SeriesItem();	
				  dto.setSeries("COUNTRY");
				  dto.setKey(decodeCountryKey((String) item.get("country")));
				  dto.setValue(Double.valueOf(item.get("cnt").toString()));
				  dto.setOrder(i++);
				  series.add(dto);				  
			}

			query = "START n=node("+user.getId()+") MATCH (n)-[r:RELATE_TO*.."+distance+"]->(m:SocialUser) WHERE m.userId<>"+id+"  WITH distinct m RETURN m.province as province,count(m) as cnt order by cnt desc, province asc";
			users =  (QueryResultBuilder) neo4jTemplate.query(query, null);
			items = users.as(Map.class).iterator();	
			 i = 0;
			while(items.hasNext()) {
				 Map item = (Map) items.next();
				  SeriesItem dto = new SeriesItem();	
				  dto.setSeries("PROVINCE");
				  dto.setKey(decodeProvinceKey((String) item.get("province")));
				  dto.setValue(Double.valueOf(item.get("cnt").toString()));
				  dto.setOrder(i++);
				  series.add(dto);				  
			}
			
			
			result.setData(series);
		
		}catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	private String decodeCountryKey(String str) {
		// TODO Auto-generated method stub
		if(StringUtils.hasText(str)) return str;
		return "未知";
	}

	private String decodeProvinceKey(String str) {
		// TODO Auto-generated method stub
		if(StringUtils.hasText(str)) return str;
		return "未知";
	}

	@Override
	public AnalyzeResult analyzeProfession(Long id, Integer distance) {
		// TODO Auto-generated method stub
		return null;
	}


}
