package jason.app.weixin.social.model;

import jason.app.weixin.common.model.Category;
import jason.app.weixin.social.constant.Status;

public class Message {
	private Long id;
	private String title;
//	private Long category;
	private Category category;
	private String content;
	private Status status;
	private String link;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	private SocialDistance socialDistance;
	
	public SocialDistance getSocialDistance() {
		return socialDistance;
	}
	public void setSocialDistance(SocialDistance socialDistance) {
		this.socialDistance = socialDistance;
	}
	private Integer sex;
	private Integer minAge;
	private Integer maxAge;
	private Integer distance;
	private Float rating;
	
	private SocialUser author;
	private String lastUpdate;
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public SocialUser getAuthor() {
		return author;
	}
	public void setAuthor(SocialUser author) {
		this.author = author;
	}
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
/**	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
*/
	
	public String getContent() {
		return content;
	}
	public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
	public void setContent(String content) {
		this.content = content;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
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
