package jason.app.weixin.common.service;

import jason.app.weixin.common.model.Category;

import java.util.List;

public interface ICategoryService {

	List<Category> findByParent(String string, Long parent);

	List<Category> findByPattern(String string, String parent);

}
