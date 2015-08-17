package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler extends MessageHandler {

	@Override
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("found more than one record, please provide more information");
        return response;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+1;
	}
}
