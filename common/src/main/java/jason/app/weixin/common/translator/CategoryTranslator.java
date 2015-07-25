package jason.app.weixin.common.translator;

import jason.app.weixin.common.entity.CategoryImpl;
import jason.app.weixin.common.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryTranslator {

    public static List<Category> toDTO(List<CategoryImpl> entities) {
        List<Category> result = new ArrayList<Category>();
        if(entities!=null) {
            for(CategoryImpl entity:entities) {
               result.add(toDTO(entity));
            }
        }
        
       
        return result;
    }

    public static Category toDTO(CategoryImpl entity) {
        Category cate = new Category();
        cate.setId(entity.getId());
        cate.setName(entity.getName());
        if(entity.getParent()!=null) {
            cate.setParent(toDTO(entity.getParent()));
        }
        cate.setType(entity.getType());
        cate.setSubType(entity.getSubType());
        
        return cate;
    }

}
