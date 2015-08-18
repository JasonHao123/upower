package jason.app.weixin.common.model;

import java.io.Serializable;

public class CreateRelationCommand implements Serializable{
	private Long fromUser;
	private Long toUser;
	private Long[] types;
	private Float rating;
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public Long[] getTypes() {
		return types;
	}
	public Long getFromUser() {
		return fromUser;
	}
	public void setFromUser(Long fromUser) {
		this.fromUser = fromUser;
	}
	public Long getToUser() {
		return toUser;
	}
	public void setToUser(Long toUser) {
		this.toUser = toUser;
	}

	public void setTypes(Long[] friendshipType) {
		// TODO Auto-generated method stub
		this.types = friendshipType;
	}
}
