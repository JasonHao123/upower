package jason.app.weixin.neo4j.service;

import java.util.List;

import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.social.model.SocialDistance;
import jason.app.weixin.social.model.SocialUser;

import org.springframework.transaction.annotation.Transactional;


public interface INeo4jService {
	@Transactional
	public void createUser(Long userId, String nickname, Integer age,Long category1,Long category2,String[] locations,String[] hobbys);

	void createRelation(Long fromUser, Long toUser, Long[] types,Float rating);

	public void createUser(SocialUser user);

	public AnalyzeResult analyzeSexAndAge(Long id, Integer distance);

	public AnalyzeResult analyzeLocation(Long id, Integer distance);

	public AnalyzeResult analyzeProfession(Long id, Integer distance);

	public List<SocialDistance> analyzeDistance(SocialUser su);

}
