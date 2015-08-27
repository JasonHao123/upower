package jason.app.weixin.social.model;

public class SocialMail {
	private Long id;
	private SocialUser from;
	private SocialUser to;
	private String message;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
