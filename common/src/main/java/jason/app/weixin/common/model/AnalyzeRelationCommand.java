package jason.app.weixin.common.model;

import java.io.Serializable;

public class AnalyzeRelationCommand implements Serializable{
	private String openid;
	private Integer distance;
	private String type;
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
