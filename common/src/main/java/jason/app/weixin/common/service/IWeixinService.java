package jason.app.weixin.common.service;

import jason.app.weixin.common.model.WeixinUser;

public interface IWeixinService {

	WeixinUser getUserInfo(String openId);

	String getTicket();

}
