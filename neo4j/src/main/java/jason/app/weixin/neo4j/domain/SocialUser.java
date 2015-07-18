package jason.app.weixin.neo4j.domain;

import java.util.Set;

import javax.xml.registry.infomodel.User;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class SocialUser {

    @GraphId
    private Long id;
    
    private Long userId;

    // Uses default schema based index
    @Indexed
    private String name;


    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<SocialRelation> getFriends() {
		return friends;
	}

	public void setFriends(Set<SocialRelation> friends) {
		this.friends = friends;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int age;

    @RelatedToVia(type = "RELATE_TO", direction = Direction.OUTGOING)
    @Fetch Set<SocialRelation> friends;

    public void addFriend(SocialUser friend,String[] types) {
    	SocialRelation relation = new SocialRelation();
    	relation.setFrom(friend);
    	relation.setTo(this);
    	relation.setTypes(types);
        this.friends.add(relation);
    }

    public SocialUser(Long id,String name, int age) {
    	this.userId= id;
        this.name = name;
        this.age = age;
    }

    public SocialUser() {
    }

    public Long getId() {
    	return id;
    }
    
    public String getName() {
        return name;
    }



	@Override
	public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		SocialUser other = (SocialUser) obj;
		if (id == null) return other.id == null;
        return id.equals(other.id);
    }
	
	@Override
    public String toString() {
        return String.format("World{name='%s', moons=%d}", name, age);
    }
}
