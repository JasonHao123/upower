package jason.app.weixin.security.repository;

import jason.app.weixin.security.entity.UserImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserImpl, Long>{

	UserImpl findByUsername(String username);

}
