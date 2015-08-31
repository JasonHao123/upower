package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.CommentImpl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentImpl, Long>{

	List<CommentImpl> findByTarget_IdOrderByCreateDateAsc(Long id, Pageable pageable);


	CommentImpl findByAuthor_IdAndTarget_Id(Long id, Long id2);

	@Query(nativeQuery=false,value="select avg(ci.rating) from CommentImpl ci where ci.target.id = :id")
	Float getUserRating(@Param("id") Long id);


}
