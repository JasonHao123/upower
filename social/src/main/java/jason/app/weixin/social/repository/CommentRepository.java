package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.CommentImpl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentImpl, Long>{

	List<CommentImpl> findByTarget_IdOrderByCreateDateAsc(Long id, Pageable pageable);


	CommentImpl findByAuthor_IdAndTarget_Id(Long id, Long id2);


}
