package jason.app.weixin.common.repository;

import jason.app.weixin.common.entity.WeixinConfigImpl;

import org.springframework.data.jpa.repository.JpaRepository;


public interface WeixinConfigRepository extends JpaRepository<WeixinConfigImpl, String>{

}
