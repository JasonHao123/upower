package jason.app.weixin.common.repository;

import jason.app.weixin.common.entity.CategoryImpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<CategoryImpl, Long>{
    
    public List<CategoryImpl> findByType(String type);

	public List<CategoryImpl> findByTypeAndParent_Id(String type, Long parent);

	public List<CategoryImpl> findByTypeAndNameLikeIgnoreCase(String type,
			String parent);
}
