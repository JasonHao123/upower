package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.MessageCommentImpl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCommentRepository extends JpaRepository<MessageCommentImpl, Long>{

	List<MessageCommentImpl> findByMessage_IdOrderByCreateDateDesc(Long id, Pageable pageable);


}
