package jason.app.weixin.social.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MessageComment {
	private Long id;
	private SocialUser author;
	@JsonIgnore
	private Message message;
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	private SocialDistance distance;
	private String content;
	private String createDate;
	private MessageComment reference;
	
	public MessageComment getReference() {
		return reference;
	}
	public void setReference(MessageComment reference) {
		this.reference = reference;
	}
	// private MessageComment replyTo;
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
	public SocialDistance getDistance() {
		return distance;
	}
	public void setDistance(SocialDistance distance) {
		this.distance = distance;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
