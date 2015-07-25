package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.MessageImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageImpl, Long>{

}
