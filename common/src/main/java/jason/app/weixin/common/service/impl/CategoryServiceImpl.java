package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.repository.CategoryRepository;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.common.translator.CategoryTranslator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> listJobTypes() {
        
        return CategoryTranslator.toDTO(categoryRepo.findByType("job.type"));
    }

    public List<Category> listEducationLevels() {
        // TODO Auto-generated method stub
    	return CategoryTranslator.toDTO(categoryRepo.findByType("education.level"));
    }

    public List<Category> findFeatureByPattern(String pattern) {
        // TODO Auto-generated method stub
    	return CategoryTranslator.toDTO(categoryRepo.findByTypeAndNameLike("feature",pattern+"%"));
    }


}
