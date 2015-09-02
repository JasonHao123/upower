package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialUserImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SocialUserRepository extends JpaRepository<SocialUserImpl, Long>{
	public List<SocialUserImpl> findByLastUpdateGreaterThan(Date date);

	public SocialUserImpl findByOpenid(String openid);

	@Query(nativeQuery=true,value="SELECT su.id FROM SOCIAL_USER su WHERE su.distanceLastSync is null OR su.distanceLastSync < :dt  order by RAND() LIMIT 1;")
	public Long findUserToAnalyze(@Param("dt") Date dt);

	@Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
	public SocialUserImpl findById(Long su);
}
