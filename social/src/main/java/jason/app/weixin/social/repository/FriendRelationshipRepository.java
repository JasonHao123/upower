package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.FriendRelationshipImpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipImpl, String>{
	public List<FriendRelationshipImpl> findByFrom_Id(Long id);
}
