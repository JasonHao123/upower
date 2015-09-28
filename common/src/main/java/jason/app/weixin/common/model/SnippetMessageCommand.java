package jason.app.weixin.common.model;

import java.io.Serializable;

public class SnippetMessageCommand implements Serializable {
	private String openId;
	private String content;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
