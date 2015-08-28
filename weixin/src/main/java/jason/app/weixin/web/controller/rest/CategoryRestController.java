package jason.app.weixin.web.controller.rest;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.service.ICategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class CategoryRestController {

    @Autowired(required=false)
    private ICategoryService categoryService;
    
    @RequestMapping(value="/category/list",method=RequestMethod.GET)
    public @ResponseBody List<Category> getLocations(@RequestParam(value="parent",required=false) Long parent) {
        List<Category> locations = categoryService.findByParent("job.category",parent);
      
        return locations;
    }

}
