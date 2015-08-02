package jason.app.weixin.social.entity;

import jason.app.weixin.social.constant.AddFriendRequestType;
import jason.app.weixin.social.constant.Status;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    
    @Column
    private AddFriendRequestType type;
    
    @Column
    private Float rating;
    
    @Column
    private String friendType;
    
    @Column
    private Status status;
    
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getFriendType() {
		return friendType;
	}

	public void setFriendType(String friendType) {
		this.friendType = friendType;
	}

	public AddFriendRequestType getType() {
		return type;
	}

	public void setType(AddFriendRequestType type) {
		this.type = type;
	}

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
