package jason.app.weixin.web.controller.weixin.model;

public class AddFriendForm {
	private String id;
	private Long[] friendshipType;
	private Float rating;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
}
