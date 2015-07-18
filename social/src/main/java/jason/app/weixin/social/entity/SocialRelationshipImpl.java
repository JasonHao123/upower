package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="FRIENDSHIP")
public class SocialRelationshipImpl {
	@Id
	private String id;
	
	@ManyToOne
	private SocialUserImpl from;
	
	@ManyToOne
	private SocialUserImpl to;
	
	@Column
	private String relationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
}
