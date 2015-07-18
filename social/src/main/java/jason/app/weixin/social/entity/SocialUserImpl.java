package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SOCIAL_USER")
public class SocialUserImpl {

	@Id
	private Long id;
	
	@Column(unique=true)
	private String nickname;
	
	@Column
	private Integer age;
	
	@Column
	private Long category1;
	
	@Column
	private Long category2;
	
	@Column
	private String locations;
	
	@Column
	private String hobbys;
	

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

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public String getHobbys() {
		return hobbys;
	}

	public void setHobbys(String hobbys) {
		this.hobbys = hobbys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
