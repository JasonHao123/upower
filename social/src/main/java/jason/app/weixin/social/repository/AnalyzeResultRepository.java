package jason.app.weixin.social.repository;

import jason.app.weixin.social.entity.AnalyzeResultImpl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyzeResultRepository extends JpaRepository<AnalyzeResultImpl, Long>{

	AnalyzeResultImpl findByKeyStr(String key);



}
