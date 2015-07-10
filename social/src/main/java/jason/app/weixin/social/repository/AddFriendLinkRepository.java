package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.AddFriendLinkImpl;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddFriendLinkRepository extends JpaRepository<AddFriendLinkImpl, String>{

	AddFriendLinkImpl findByUser_IdAndCreateDateGreaterThan(Long id, Date time);


}
