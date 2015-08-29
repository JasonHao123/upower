package jason.app.weixin.common.model;

import java.io.Serializable;

public class AnalyzeRelationCommand implements Serializable{
	private String openid;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public AnalyzeRelationCommand(){}
	public AnalyzeRelationCommand(String openid) {
		// TODO Auto-generated constructor stub
		this.openid = openid;
	}

}
