package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SOCIAL_DISTANCE")
public class SocialDistanceImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @ManyToOne
	private SocialUserImpl fromUser;
    @ManyToOne
	private SocialUserImpl toUser;
    @Column
	private Integer distance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SocialUserImpl getFromUser() {
		return fromUser;
	}
	public void setFromUser(SocialUserImpl fromUser) {
		this.fromUser = fromUser;
	}
	public SocialUserImpl getToUser() {
		return toUser;
	}
	public void setToUser(SocialUserImpl toUser) {
		this.toUser = toUser;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
}
