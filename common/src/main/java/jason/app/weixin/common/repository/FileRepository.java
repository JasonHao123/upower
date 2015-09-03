package jason.app.weixin.common.repository;

import jason.app.weixin.common.entity.FileImpl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileImpl, Long>{

	List<FileImpl> findByUserIdAndCreateDateGreaterThanAndCreateDateLessThan(Long id,
			Date start, Date end);
    
}
