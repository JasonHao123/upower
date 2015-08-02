package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRelationshipRepository extends JpaRepository<SocialRelationshipImpl, String>{
	public List<SocialRelationshipImpl> findByFrom_Id(Long id);

	public SocialRelationshipImpl findByFrom_IdAndTo_Id(Long id, Long id2);

	public Page<SocialRelationshipImpl> findByFrom_Id(Long user, Pageable pageable);

	public List<SocialRelationshipImpl> findByLastUpdateGreaterThan(Date time);
}
