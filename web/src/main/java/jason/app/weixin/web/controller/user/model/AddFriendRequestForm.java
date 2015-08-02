package jason.app.weixin.web.controller.user.model;

public class AddFriendRequestForm {
	private Long userId;
	private Long[] friendshipType;
	private Float rating;
	private String message;
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long[] getFriendshipType() {
		return friendshipType;
	}
	public void setFriendshipType(Long[] friendshipType) {
		this.friendshipType = friendshipType;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
