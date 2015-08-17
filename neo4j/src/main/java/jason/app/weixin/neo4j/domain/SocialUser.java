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
    
    @Indexed
    private Long userId;

    // Uses default schema based index
    @Indexed
    private String name;
    
    private Long category1;
    
    private Long category2;
    
    private String[] locations;
    
    private String[] hobbys;
    
    private String country;
    
    private String city;
    
    private String province;
    
    private Integer sex;


    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getCategory1() {
		return category1;
	}

	public void setCategory1(Long category1) {
		this.category1 = category1;
	}

	public Long getCategory2() {
		return category2;
	}

	public void setCategory2(Long category2) {
		this.category2 = category2;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public String[] getHobbys() {
		return hobbys;
	}

	public void setHobbys(String[] hobbys) {
		this.hobbys = hobbys;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
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

	private Integer age;

    @RelatedToVia(type = "RELATE_TO", direction = Direction.OUTGOING)
    @Fetch Set<SocialRelation> friends;

    public void addFriend(SocialUser friend,Long[] types) {
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
