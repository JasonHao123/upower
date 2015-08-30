package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;

import org.springframework.stereotype.Component;

@Component
public class HelpMessageHandler extends MessageHandler {

	@Override
	public boolean canHandle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		return super.canHandle(params, header) && ("help".equalsIgnoreCase(params.getContent()) || "帮助".equals(params.getContent()));
	}
	
	@Override
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("支持以下命令：<ul><li>个人首页</li><li>邀请好友</li><li>好友</li><li>私信</li><li>社交分析</li></ul>");
        return response;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder()+98;
	}
}
