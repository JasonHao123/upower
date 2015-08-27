package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialMailImpl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMailRepository extends JpaRepository<SocialMailImpl, Long>{

	List<SocialMailImpl> findByFrom_IdInAndTo_IdInOrderByCreateDateDesc(
			List<Long> users, List<Long> users2, Pageable pageable);

}
