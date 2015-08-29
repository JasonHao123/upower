package jason.app.weixin.neo4j.controller;

import jason.app.weixin.neo4j.domain.SocialUser;
import jason.app.weixin.social.model.SocialRelationDTO;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class MessageTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private Neo4jTemplate neo4jTemplate;

    @Test
    public void testMessageSource() {
		try {
		Long userId = 1L;
		String query = "MATCH p = shortestPath(one-[:RELATE_TO*.."+3+"]->(two:SocialUser)) WHERE one.userId = {userId} and two.userId <> {userId}  RETURN distinct one.userId as from,two.userId as to,length(p) as distance";
		Hashtable<String, Object> map = new Hashtable<String, Object>();
		map.put("userId",userId);
		QueryResultBuilder users =  (QueryResultBuilder) neo4jTemplate.query(query, map);
		Iterator<Map> items = users.as(Map.class).iterator();
		
		while(items.hasNext()) {
			 Map item = (Map) items.next();
			 final SocialRelationDTO dto = new SocialRelationDTO();
	
				BeanUtils.copyProperties(dto,item);
				
		}

		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
}
