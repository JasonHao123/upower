package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialMessageImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialMessageRepository extends JpaRepository<SocialMessageImpl, Long>{

	Page<SocialMessageImpl> findByUser_Id(Long id,Pageable pageable);

	Page<SocialMessageImpl> findByUser_IdAndMessage_Category(Long id, Long category,Pageable pageable);

}
