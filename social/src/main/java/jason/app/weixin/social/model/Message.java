package jason.app.weixin.social.model;

import jason.app.weixin.common.model.Category;

public class Message {
	private Long id;
	private SocialUser author;
	private String title;
	private Category category;
	private Integer distance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	private String content;
	public SocialUser getAuthor() {
		return author;
	}
	public void setAuthor(SocialUser author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
