package jason.app.weixin.common.model;

import java.io.Serializable;

public class CreateUserCommand implements Serializable{
	private Long userId;
	private String openId;
	public CreateUserCommand(){}
	public CreateUserCommand(Long userId,String openId) {
		this.userId = userId;
		this.openId = openId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
