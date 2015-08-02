package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialUserImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialDistanceRepository extends JpaRepository<SocialDistanceImpl, Long>{
	public SocialDistanceImpl findByFromUser_IdAndToUser_Id(Long fromId,Long toId);

	public List<SocialDistanceImpl> findByFromUser_IdAndDistanceLessThan(Long id,int i);

	public Page<SocialDistanceImpl> findByFromUser_IdAndDistanceLessThanEqual(
			Long id, Integer personalCircal, Pageable pageable);

	public List<SocialDistanceImpl> findByFromUser_IdAndDistanceLessThanEqual(
			Long id, Integer personalCircal);
}
