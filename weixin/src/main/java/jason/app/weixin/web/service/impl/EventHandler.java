package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;
import jason.app.weixin.web.service.Handler;

import org.springframework.util.StringUtils;

public abstract class EventHandler implements Handler {

	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return params!=null && StringUtils.hasText(params.getEvent());
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 100;
	}

}
