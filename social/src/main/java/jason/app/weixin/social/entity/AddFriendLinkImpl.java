package jason.app.weixin.social.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ADD_FRIEND_LINK")
public class AddFriendLinkImpl {
	@Id
	private String id;
	
	@ManyToOne
	private SocialUserImpl user;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SocialUserImpl getUser() {
		return user;
	}

	public void setUser(SocialUserImpl user) {
		this.user = user;
	}
	
}
