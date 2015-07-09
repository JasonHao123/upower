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
public class SkillRestController {

    @Autowired(required=false)
    private ICategoryService skillService;
    
    @RequestMapping(value="/skill/search",method=RequestMethod.GET)
    public @ResponseBody List<Category> getLocationsByPattern(@RequestParam(value="prefix",required=false) String parent) {
        List<Category> locations = skillService.findByPattern("skill",parent);
      
        return locations;
    }
}
