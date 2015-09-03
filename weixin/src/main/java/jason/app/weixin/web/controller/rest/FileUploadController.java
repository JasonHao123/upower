package jason.app.weixin.web.controller.rest;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;
import jason.app.weixin.common.service.IFileService;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.web.controller.rest.model.Image;
import jason.app.weixin.web.controller.rest.model.UploadResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class FileUploadController {
	
	@Autowired
	private IFileService fileService;
	
	@Autowired
	private ISecurityService securityService;
	
	private Calendar calendar = Calendar.getInstance();

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
		User user = securityService.getCurrentUser();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date lastDay = calendar.getTime();
		List<FileItem> files = fileService.findImagesByUser(user.getId(),lastDay,new Date());
		
		for(FileItem file:files) {
			Image image = new Image();
			image.setImage(file.getUrl());
			image.setThumb(file.getThumbnail());
			image.setFilelink(file.getUrl());
			image.setFolder("过去一天");
			images.add(image);
		}
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7);
		Date lastWeek = calendar.getTime();
		files = fileService.findImagesByUser(user.getId(),lastWeek,lastDay);
		
		for(FileItem file:files) {
			Image image = new Image();
			image.setImage(file.getUrl());
			image.setThumb(file.getThumbnail());
			image.setFilelink(file.getUrl());
			image.setFolder("过去一周");
			images.add(image);
		}
		
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		Date lastMonth = calendar.getTime();
		files = fileService.findImagesByUser(user.getId(),lastMonth,lastWeek);
		
		for(FileItem file:files) {
			Image image = new Image();
			image.setImage(file.getUrl());
			image.setThumb(file.getThumbnail());
			image.setFilelink(file.getUrl());
			image.setFolder("过去一月");
			images.add(image);
		}
		
		
		return images;
    }

}
