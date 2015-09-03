package jason.app.weixin.web.controller.rest;

import jason.app.weixin.web.controller.rest.model.Image;
import jason.app.weixin.web.controller.rest.model.UploadResult;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class FileUploadController {

	@RequestMapping(value = "/upload")
    public @ResponseBody UploadResult handleFormUpload(HttpServletRequest req,HttpServletResponse resp){
		UploadResult result = new UploadResult();
		return result;
    }
	
	@RequestMapping(value = "/uploadFile")
    public @ResponseBody UploadResult handleFormUploadFile(HttpServletRequest req,HttpServletResponse resp){
		UploadResult result = new UploadResult();
		return result;
    }

	@RequestMapping(value = "/images")
    public @ResponseBody List<Image> images(HttpServletRequest req,HttpServletResponse resp){
		List<Image> images = new ArrayList<Image>();
		for(int i=0;i<10;i++) {
			Image image = new Image();
			image.setFolder("folder"+i%2);
			images.add(image);
		}
		return images;
    }

}
