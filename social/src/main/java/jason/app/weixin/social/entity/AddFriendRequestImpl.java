package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ADD_FRIEND_REQUEST")
public class AddFriendRequestImpl {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @ManyToOne
	private SocialUserImpl from;
	
    @ManyToOne
	private SocialUserImpl to;
	
    @Column
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocialUserImpl getFrom() {
		return from;
	}

	public void setFrom(SocialUserImpl from) {
		this.from = from;
	}

	public SocialUserImpl getTo() {
		return to;
	}

	public void setTo(SocialUserImpl to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
