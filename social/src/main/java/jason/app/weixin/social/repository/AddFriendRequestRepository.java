package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.AddFriendRequestImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddFriendRequestRepository extends JpaRepository<AddFriendRequestImpl, Long>{

	Page<AddFriendRequestImpl> findByTo_IdAndStatusIsNull(Long id, Pageable pageable);

}
