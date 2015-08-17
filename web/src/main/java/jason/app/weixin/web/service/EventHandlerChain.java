package jason.app.weixin.web.service;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

public interface EventHandlerChain {

	WeixinParam handle(WeixinParam params, WeixinHeader header);

}
