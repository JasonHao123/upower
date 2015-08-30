package jason.app.weixin.social.model;

public class Comment {
	private Long id;
	private SocialUser author;
	
	private SocialUser target;
	public SocialUser getTarget() {
		return target;
	}
	public void setTarget(SocialUser target) {
		this.target = target;
	}
	private String message;
	private Float rating;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SocialUser getAuthor() {
		return author;
	}
	public void setAuthor(SocialUser author) {
		this.author = author;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
}
