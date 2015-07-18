package jason.app.weixin.neo4j.repository;

import jason.app.weixin.neo4j.domain.SocialUser;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * @author mh
 * @since 02.04.11
 */
public interface SocialUserRepository extends GraphRepository<SocialUser>, RelationshipOperationsRepository<SocialUser> {
	public SocialUser findByUserId(Long userId);
}
