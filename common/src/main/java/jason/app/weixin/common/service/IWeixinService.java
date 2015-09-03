package jason.app.weixin.common.service;

import java.io.File;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.WeixinUser;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface IWeixinService {

	WeixinUser getUserInfo(String openId);

	String getTicket();
	 
	public void postMessage(SendMessageCommand msg) throws Exception;

	String getAppId();

	String getSecret();
	@Transactional
	void refreshAccessToken();

	FileInfo downloadMedia(String mediaId) throws Exception;

}
