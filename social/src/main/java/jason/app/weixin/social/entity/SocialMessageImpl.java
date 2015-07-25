package jason.app.weixin.social.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SOCIAL_MESSAGE")
public class SocialMessageImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @ManyToOne
	private SocialUserImpl user;
    
 //   @ManyToOne
//	private SocialGroupImpl group;
    
    @ManyToOne
	private MessageImpl message;

	public MessageImpl getMessage() {
		return message;
	}

	public void setMessage(MessageImpl message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocialUserImpl getUser() {
		return user;
	}

	public void setUser(SocialUserImpl user) {
		this.user = user;
	}

	
}
