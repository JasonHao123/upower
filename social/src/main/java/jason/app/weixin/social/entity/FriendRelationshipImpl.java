package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="FRIENDSHIP")
public class FriendRelationshipImpl {
	@Id
	private String id;
	
	@ManyToOne
	private SocialUserImpl from;
	
	@ManyToOne
	private SocialUserImpl to;
	
	@Column
	private String relationType;
}
