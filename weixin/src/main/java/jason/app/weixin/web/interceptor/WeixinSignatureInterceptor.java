package jason.app.weixin.web.interceptor;

import jason.app.weixin.common.service.IWeixinService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class WeixinSignatureInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinSignatureInterceptor.class);
	
	@Autowired
	private IWeixinService weixinService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		try {
		String timestamp =""+(int) (System.currentTimeMillis() / 1000L);
		String noncestr = DigestUtils.md5Hex(timestamp);
		StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();
	    String url;
	    if (queryString == null) {
	        url= requestURL.toString();
	    } else {
	        url= requestURL.append('?').append(queryString).toString();
	    }
		String str = "jsapi_ticket="+weixinService.getTicket()+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String signature = DigestUtils.sha1Hex(str);
		request.setAttribute("appId", weixinService.getAppId());
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("signature", signature);
		}catch(Exception e) {
			logger.warn("Failed to generate weixin signature due to "+e.getMessage());
		}
		return super.preHandle(request, response, handler);
	}
}
