package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SocialUserImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUserImpl, Long>{

}
