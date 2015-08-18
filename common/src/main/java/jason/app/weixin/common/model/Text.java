package jason.app.weixin.common.model;

import java.io.Serializable;

public class Text implements Serializable{
	private String content;
	public Text(){}
	public Text(String content) {
		// TODO Auto-generated constructor stub
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
