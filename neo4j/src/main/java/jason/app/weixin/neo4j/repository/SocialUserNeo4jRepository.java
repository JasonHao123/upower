package jason.app.weixin.neo4j.repository;

import jason.app.weixin.neo4j.domain.SocialRelation;
import jason.app.weixin.neo4j.domain.SocialUser;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Path;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * @author mh
 * @since 02.04.11
 */
public interface SocialUserNeo4jRepository extends GraphRepository<SocialUser>, RelationshipOperationsRepository<SocialUser> {
	public SocialUser findByUserId(Long userId);
	
	 @Query("start usr=node({0}),other=node({1}) match usr-[r:RELATE_TO]->other  return r")
	  SocialRelation findByFromAndTo(Long user,Long other);
	 
//	 @Query("start usr=node({0})  MATCH p = shortestPath(one-[:RELATE_TO*..6]-(two:SocialUser)) WHERE length(p) <= {1} RETURN distinct two")
//	  List<SocialUser> findUserFriends(Long user,int scope);
	 
	 @Query("start usr=node({0}) MATCH p = shortestPath(one-[:RELATE_TO*..6]-(two:SocialUser)) WHERE length(p) <= {1} RETURN distinct p")
	  List<Map<String,Path>> findUserRelations(Long user,int scope);
	 
	 
}
