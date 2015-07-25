package jason.app.weixin.social.repository;

import java.util.List;

import jason.app.weixin.social.entity.SocialDistanceImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialDistanceRepository extends JpaRepository<SocialDistanceImpl, Long>{
	public SocialDistanceImpl findByFromUser_IdAndToUser_Id(Long fromId,Long toId);

	public List<SocialDistanceImpl> findByFromUser_IdAndDistanceLessThan(Long id,int i);
}
