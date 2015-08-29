package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialUserImpl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUserImpl, Long>{
	public List<SocialUserImpl> findByLastUpdateGreaterThan(Date date);

	public SocialUserImpl findByOpenid(String openid);
}
