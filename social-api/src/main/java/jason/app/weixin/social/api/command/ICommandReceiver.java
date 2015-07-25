package jason.app.weixin.social.api.command;

import org.springframework.transaction.annotation.Transactional;


public interface ICommandReceiver {
	@Transactional
	public void createUser(Long userId, String nickname, Integer age,Long category1,Long category2,String[] locations,String[] hobbys);

	void createRelation(Long fromUser, Long toUser, Long[] types);

	public void analyzeUserRelationDistance(Long userId, Integer extensiveLevel);

}
