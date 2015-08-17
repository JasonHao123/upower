package jason.app.weixin.web.service;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

public interface Handler {
	public boolean canHandle(WeixinParam params, WeixinHeader header);
	public WeixinParam handle(WeixinParam params, WeixinHeader header);
	public int getOrder();
}
