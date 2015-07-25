package jason.app.weixin.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

/**
 * @author mh
 * @since 04.03.11
 */
@RelationshipEntity
public class SocialRelation {

    @GraphId Long id;

    @StartNode SocialUser from;
    @EndNode SocialUser to;
    
    Long[] types;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocialUser getFrom() {
		return from;
	}

	public void setFrom(SocialUser from) {
		this.from = from;
	}

	public SocialUser getTo() {
		return to;
	}

	public void setTo(SocialUser to) {
		this.to = to;
	}

	public Long[] getTypes() {
		return types;
	}

	public void setTypes(Long[] types) {
		this.types = types;
	}
   


}
