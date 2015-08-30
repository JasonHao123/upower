package jason.app.weixin.social.model;

public class SocialMail {
	private Long id;
	private SocialUser from;
	private SocialUser to;
	private String message;
	private String createDate;
	private boolean self;
	public boolean isSelf() {
		return self;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
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
	public void setSelf(boolean b) {
		// TODO Auto-generated method stub
		this.self = b;
	}
}
