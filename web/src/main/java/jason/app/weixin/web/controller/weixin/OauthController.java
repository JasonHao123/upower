package jason.app.weixin.web.controller.weixin;

import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.service.ISocialService;
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
@RequestMapping("/weixin/oauth")
public class OauthController {

	// @Autowired
	// private AuthenticationSuccessHandler handler;
	private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private ISecurityService facade;

	@Autowired
	private ISocialService socialService;

	private ObjectMapper mapper = new ObjectMapper();

	String apiKey = "wxbe821ceae333b377";
	String apiSecret = "d8578702710ff6a5d17efc6338efc08a";
	private static final Token EMPTY_TOKEN = null;
	private static final String PROTECTED_RESOURCE_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
	OAuthService service = new ServiceBuilder().provider(WeixinApi.class)
			.apiKey(apiKey).apiSecret(apiSecret).scope("snsapi_base")
			.callback("http://www.weaktie.cn/weixin/oauth/callback.do").build();
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
			return "weixin.subscribe";
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
					facade.loginExternalUser(req, resp, response2.getOpenid());
					SavedRequest request = requestCache.getRequest(req, resp);
					if(request!=null && StringUtils.hasText(request.getRedirectUrl())) {
						resp.sendRedirect(request.getRedirectUrl());
					}else {
						return "weixin.subscribe";
					}
				} else {
					return "weixin.subscribe";
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "weixin.subscribe";
			}
		}
		return null;
	}
}
