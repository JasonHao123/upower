package jason.app.weixin.social.model;

public class SocialMessage {
	private Long id;
	private SocialUser user;
	private Message message;
	public SocialUser getUser() {
		return user;
	}

	public void setUser(SocialUser user) {
		this.user = user;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
