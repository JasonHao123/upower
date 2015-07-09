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

	@Override
	public List<Category> findByParent(String type, Long parent) {
		// TODO Auto-generated method stub
        return CategoryTranslator.toDTO(categoryRepo.findByTypeAndParent_Id(type,parent));
	}

	@Override
	public List<Category> findByPattern(String type, String parent) {
		// TODO Auto-generated method stub
        return CategoryTranslator.toDTO(categoryRepo.findByTypeAndNameLikeIgnoreCase(type,parent+"%"));

	}


}
