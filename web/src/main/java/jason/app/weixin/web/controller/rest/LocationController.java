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
public class LocationController {

    @Autowired(required=false)
    private ICategoryService locationService;
    
    @RequestMapping(value="/location/list",method=RequestMethod.GET)
    public @ResponseBody List<Category> getLocations(@RequestParam(value="parent",required=false) Long parent) {
        List<Category> locations = locationService.findByParent("location",parent);
      
        return locations;
    }
    
    @RequestMapping(value="location/search",method=RequestMethod.GET)
    public @ResponseBody List<Category> getLocationsByPattern(@RequestParam(value="prefix",required=false) String parent) {
        List<Category> locations = locationService.findByPattern("location",parent+"%");
        for(Category category:locations) {
        	if("district" == category.getSubType()) {
        		category.setName(category.getParent().getName()+" "+category.getName());
        	}
        }
        return locations;
    }
}
