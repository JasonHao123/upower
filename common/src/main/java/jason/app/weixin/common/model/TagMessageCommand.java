package jason.app.weixin.common.model;

import java.io.Serializable;

public class TagMessageCommand implements Serializable {
	private String openId;
	private String tags;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}
