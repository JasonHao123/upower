package jason.app.weixin.common.model;

import java.io.Serializable;

public class PublishMessageCommand implements Serializable{
	private Long messageId;
	private Long socialMessageId;
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
