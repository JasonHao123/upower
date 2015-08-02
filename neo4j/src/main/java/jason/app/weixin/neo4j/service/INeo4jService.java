package jason.app.weixin.neo4j.service;

import org.springframework.transaction.annotation.Transactional;


public interface INeo4jService {
	@Transactional
	public void createUser(Long userId, String nickname, Integer age,Long category1,Long category2,String[] locations,String[] hobbys);

	void createRelation(Long fromUser, Long toUser, Long[] types,Float rating);

	@Transactional
	public void analyzeUserRelationDistance(Long userId, Integer extensiveLevel);

}
