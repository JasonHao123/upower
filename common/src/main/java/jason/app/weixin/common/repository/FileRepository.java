package jason.app.weixin.common.repository;

import jason.app.weixin.common.constant.MediaType;
import jason.app.weixin.common.entity.FileImpl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileImpl, Long>{

	List<FileImpl> findByUserIdAndMediaTypeAndCreateDateGreaterThanAndCreateDateLessThan(
			Long id, MediaType type, Date start, Date end);
    
}
