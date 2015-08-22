package jason.app.weixin.web.controller.weixin;

import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.web.controller.weixin.model.AuthorizeResponse;
import jason.app.weixin.web.oauth.WeixinApi;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class OauthController implements InitializingBean{

	// @Autowired
	// private AuthenticationSuccessHandler handler;
	private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private ISecurityService facade;
	
	@Autowired
	private IWeixinService weixinService;

	private ObjectMapper mapper = new ObjectMapper();

	private static final Token EMPTY_TOKEN = null;
	private static final String PROTECTED_RESOURCE_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";

	private static final String CALL_BACK_URL = "http://www.weaktie.cn/weixin/oauth/callback.do";
	
	private OAuthService service;
	private static Logger logger = LoggerFactory
			.getLogger(WeixinProcessor.class);

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
	public String post(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, Model model,
			@RequestParam(value = "code", required = false) String code) {
		logger.info("code:" + code);
		if (code == null) {
			String line = null;
			StringBuffer result = new StringBuffer();
			try {
				BufferedReader reader = new BufferedReader(req.getReader());

				if ((line = reader.readLine()) != null) {
					result.append(line + "<br>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("body", result.toString());
			return "login.fail";
		} else {
			Verifier verifier = new Verifier(code);
			Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			session.setAttribute("accessToken", accessToken);
			logger.info("(if your curious it looks like this: " + accessToken
					+ " )");

			try {
				AuthorizeResponse response2 = mapper.readValue(
						accessToken.getRawResponse(), AuthorizeResponse.class);
				model.addAttribute("body", accessToken.getRawResponse());
				if (response2 != null && response2.getOpenid() != null) {

					if(facade.loginExternalUser(req, resp, response2.getOpenid())){
						SavedRequest request = requestCache.getRequest(req, resp);
						if(request!=null && StringUtils.hasText(request.getRedirectUrl())) {
							resp.sendRedirect(request.getRedirectUrl());
						}else {
							return "login.fail";
						}
					}else {
						return "login.fail";
					}
				} else {
					return "login.fail";
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "login.fail";
			}
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		service = new ServiceBuilder().provider(WeixinApi.class)
					.apiKey(weixinService.getAppId()).apiSecret(weixinService.getSecret()).scope("snsapi_base")
					.callback(CALL_BACK_URL).build();
	}
}
