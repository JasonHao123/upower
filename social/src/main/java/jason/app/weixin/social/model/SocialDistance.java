package jason.app.weixin.social.model;

public class SocialDistance {
	private SocialUser from;
	private SocialUser to;
	private Integer distance;
	private Float rating;
	public SocialUser getFrom() {
		return from;
	}
	public void setFrom(SocialUser from) {
		this.from = from;
	}
	public SocialUser getTo() {
		return to;
	}
	public void setTo(SocialUser to) {
		this.to = to;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
}
