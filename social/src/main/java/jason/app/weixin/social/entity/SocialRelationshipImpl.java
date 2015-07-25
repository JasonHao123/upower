package jason.app.weixin.social.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column
	private Float rating;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

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
