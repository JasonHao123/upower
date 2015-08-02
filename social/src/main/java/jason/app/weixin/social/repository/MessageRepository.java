package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.MessageImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageImpl, Long>{

	Page<MessageImpl> findByAuthor_Id(Long id, Pageable pageable);

	Page<MessageImpl> findByAuthor_IdOrderByLastUpdateDesc(Long id,
			Pageable pageable);

}
