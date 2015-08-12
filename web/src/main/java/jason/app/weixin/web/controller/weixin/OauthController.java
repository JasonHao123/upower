package jason.app.weixin.web.controller.weixin;

import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.web.controller.weixin.model.AuthorizeResponse;
import jason.app.weixin.web.controller.weixin.model.WeixinUserInfo;
import jason.app.weixin.web.oauth.WeixinApi;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weixin/oauth")
public class OauthController {
	
    @Autowired
    private ISecurityService facade;
    
    @Autowired
    private ISocialService socialService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
	 String apiKey = "wxbe821ceae333b377";
	    String apiSecret = "d8578702710ff6a5d17efc6338efc08a";
	    private static final Token EMPTY_TOKEN = null;
		private static final String PROTECTED_RESOURCE_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
	    OAuthService service = new ServiceBuilder()
     .provider(WeixinApi.class)
     .apiKey(apiKey)
     .apiSecret(apiSecret)
     .scope("snsapi_userinfo")
     .callback("http://www.openpub.cn/weixin/oauth/callback.do")
     .build();
	private static Logger logger = LoggerFactory.getLogger(WeixinProcessor.class);
	

	@RequestMapping("/authorize")
	public void post(HttpServletRequest request, HttpServletResponse response) {
		    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		    try {
				response.sendRedirect(authorizationUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/callback")
	@Transactional
	public String post(HttpServletRequest req,HttpServletResponse resp,HttpSession session,Model model,@RequestParam(value="code",required=false) String code) {
		logger.info("code:"+code);
		Verifier verifier = new Verifier(code);
		Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		session.setAttribute("accessToken", accessToken);
		logger.info("(if your curious it looks like this: " + accessToken + " )");
				
		try {
			AuthorizeResponse response2 = mapper.readValue(accessToken.getRawResponse(),AuthorizeResponse.class);

			if(response2!=null && response2.getOpenid()!=null) {
				session.setAttribute("openid", response2.getOpenid());
		User user = facade.findExternalUser(response2.getOpenid());
		if(user==null) {
            user = facade.createExternalUser(response2.getOpenid(), response2.getOpenid(), Arrays.asList(new String[]{"ROLE_USER"}));			
		}
		facade.loginExternalUser(req,resp,response2.getOpenid());

		//TODO need to move it to update user profile function;
		try {
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		request.addQuerystringParameter("access_token", accessToken.getToken());
		request.addQuerystringParameter("openid", response2.getOpenid());
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    logger.info("Got it! Lets see what we found...");
	    logger.info(""+response.getCode());
	    logger.info(response.getBody());
	    model.addAttribute("body",response.getBody());
		WeixinUserInfo userInfo = mapper.readValue(response.getBody(),WeixinUserInfo.class);
		model.addAttribute("userInfo",userInfo);
		if(userInfo!=null && user!=null) {
	        final SocialUser profile = new SocialUser();
	        profile.setId(user.getId());
	        profile.setNickname(userInfo.getNickname());
	        profile.setCountry(userInfo.getCountry());
	        profile.setProvince(userInfo.getProvince());
	        profile.setCity(userInfo.getCity());
	        profile.setHeadimgurl(userInfo.getHeadimgurl());
	        profile.setSex(userInfo.getSex());
	        socialService.saveProfile(profile);		}
		}catch(Exception e) {
			e.printStackTrace();
		}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/check.do";
	}
}
