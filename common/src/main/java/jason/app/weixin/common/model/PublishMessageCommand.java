package jason.app.weixin.common.model;

import java.io.Serializable;

public class PublishMessageCommand implements Serializable{
	private Long messageId;
	private Long socialMessageId;
	private String openid;
	private String title;
	private String url;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getSocialMessageId() {
		return socialMessageId;
	}
	public void setSocialMessageId(Long socialMessageId) {
		this.socialMessageId = socialMessageId;
	}
	public PublishMessageCommand(){}
	public PublishMessageCommand(Long id) {
		// TODO Auto-generated constructor stub
		this.messageId = id;
	}
	public PublishMessageCommand(Long id,Long socialMessageId) {
		// TODO Auto-generated constructor stub
		this.messageId = id;
		this.socialMessageId = socialMessageId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
