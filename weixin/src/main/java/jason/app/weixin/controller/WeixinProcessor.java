package jason.app.weixin.controller;


import jason.app.weixin.controller.model.AuthorizeResponse;
import jason.app.weixin.controller.model.WeixinHeader;
import jason.app.weixin.controller.model.WeixinParam;
import jason.app.weixin.oauth.WeixinApi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin")
public class WeixinProcessor
{
    
    private ObjectMapper mapper = new ObjectMapper();
    
	 String apiKey = "wxbe821ceae333b377";
	    String apiSecret = "d8578702710ff6a5d17efc6338efc08a";
	    private static final Token EMPTY_TOKEN = null;
		private static final String PROTECTED_RESOURCE_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
	    OAuthService service = new ServiceBuilder()
        .provider(WeixinApi.class)
        .apiKey(apiKey)
        .apiSecret(apiSecret)
        .scope("snsapi_base")
        .callback("http://www.openpub.cn/spring/weixin/post2")
        .build();
	private static Logger logger = LoggerFactory.getLogger(WeixinProcessor.class);
	@RequestMapping(value="/processor",produces="application/xml")
    public @ResponseBody WeixinParam home(@RequestBody WeixinParam params,@ModelAttribute WeixinHeader header)
    {
		logger.info(header.getEchostr());
		/**
		if(StringUtils.hasText(header.getEchostr())) {
			return header.getEchostr();
		}
		*/
		logger.info(params.toString());
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("found more than one record, please provide more information");
        logger.info(response.toString());
        return response;
    }

	@RequestMapping("/post")
	public void post(HttpServletRequest request, HttpServletResponse response) {


		    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		    try {
				response.sendRedirect(authorizationUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/post2")
	public String post(Model model,@RequestParam(value="code",required=false) String code) {
		logger.info("code:"+code);
		Verifier verifier = new Verifier(code);
		Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		logger.info("(if your curious it looks like this: " + accessToken + " )");
		try {
			AuthorizeResponse response2 = mapper.readValue(accessToken.getRawResponse(),AuthorizeResponse.class);
			 model.addAttribute("body",response2.getOpenid());
		}catch(Exception e) {
			e.printStackTrace();
		}
		/**
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		request.addQuerystringParameter("access_token", accessToken.getToken());
		request.addQuerystringParameter("openid", "oDUuBw4rBU_IJO0Oclrl6JPolu-c");
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    System.out.println("Got it! Lets see what we found...");
	    System.out.println();
	    System.out.println(response.getCode());
	    System.out.println(response.getBody());
	    model.addAttribute("body",response.getBody());
	    */
		return "post";
	}

}
