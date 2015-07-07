package jason.app.weixin.security.repository;

import jason.app.weixin.security.entity.RoleImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleImpl, String>{

}
