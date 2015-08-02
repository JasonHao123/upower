package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.SettingsImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<SettingsImpl, Long>{

}
