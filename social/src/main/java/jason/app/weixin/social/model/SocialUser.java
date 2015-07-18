package jason.app.weixin.social.model;

public class SocialUser {
    private Long id;
    private String nickname;
    private Integer age;
    private Long category1;
    private Long category2;
    private String[] location;
    private String[] hobby;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Long getCategory1() {
		return category1;
	}
	public void setCategory1(Long category1) {
		this.category1 = category1;
	}
	public Long getCategory2() {
		return category2;
	}
	public void setCategory2(Long category2) {
		this.category2 = category2;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public String[] getHobby() {
		return hobby;
	}
	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}
}
