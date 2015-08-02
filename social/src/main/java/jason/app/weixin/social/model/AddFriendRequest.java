package jason.app.weixin.social.model;

public class AddFriendRequest {
	private Long id;
	private SocialUser fromUser;

	public SocialUser getFromUser() {
		return fromUser;
	}
	public void setFromUser(SocialUser fromUser) {
		this.fromUser = fromUser;
	}
	private Long[] friendshipType;
	private Float rating;
	private String message;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
