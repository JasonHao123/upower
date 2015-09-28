package jason.app.weixin.common.model;

import java.io.Serializable;

public class LinkMessageCommand implements Serializable{
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
}
