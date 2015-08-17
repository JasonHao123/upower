package jason.app.weixin.web.controller.weixin;

import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin")
public class WeixinController {
	
	String noncestr = "Wm3WZYTPz0wzccnW";
	String ticket = "kgt8ON7yVITDhtdwci0qedVBEbIY_RWFvlsVv6FEXEcXG5zpJg_m6O82pxkJT3zV4nnQP9j261bZFMdL_JW1Dw";

	String appId = "wxbe821ceae333b377";


	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private ISocialService socialService;

	@RequestMapping(value = "/invite", method = RequestMethod.GET)
	public String invite(HttpServletRequest request,Model model) {
		User currentUser = securityService.getCurrentUser();
		SocialUser profile = socialService.loadProfile(currentUser.getId());
		model.addAttribute("profile", profile);
		UUID uuid  =  UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		
		String timestamp =""+(int) (System.currentTimeMillis() / 1000L);
		StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();
	    String url;
	    if (queryString == null) {
	        url= requestURL.toString();
	    } else {
	        url= requestURL.append('?').append(queryString).toString();
	    }
		String str = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String signature = DigestUtils.sha1Hex(str);
		model.addAttribute("appId", appId);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("noncestr", noncestr);
		model.addAttribute("signature", signature);
		
		return "weixin.invite";
	}
	
	@RequestMapping(value = "/invite2", method = RequestMethod.GET)
	public @ResponseBody String invite2(@RequestParam("id") String id,@RequestParam("type") String type) {
		return "test";
	}
}
