package jason.app.weixin.common.service;

import jason.app.weixin.common.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> listJobTypes();

    List<Category> listEducationLevels();

    List<Category> findFeatureByPattern(String pattern);

}
