package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import org.springframework.stereotype.Component;


public class CommandUnknownMessageHandler extends MessageHandler {

	
	@Override
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("输入的命令无法识别，请输入帮助或help来获得帮助");
        return response;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+99;
	}
}
