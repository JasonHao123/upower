package jason.app.weixin.security.repository;

import jason.app.weixin.security.entity.RememberMeTokenImpl;
import jason.app.weixin.security.entity.UserImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RememberTokenRepository extends JpaRepository<RememberMeTokenImpl, String>{

}
