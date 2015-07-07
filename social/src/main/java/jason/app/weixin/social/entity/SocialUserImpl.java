package jason.app.weixin.social.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SOCIAL_USER")
public class SocialUserImpl {

	@Id
	private Long id;
	
	@OneToMany
	private List<SocialUserImpl> friends;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SocialUserImpl> getFriends() {
		return friends;
	}

	public void setFriends(List<SocialUserImpl> friends) {
		this.friends = friends;
	}
}
