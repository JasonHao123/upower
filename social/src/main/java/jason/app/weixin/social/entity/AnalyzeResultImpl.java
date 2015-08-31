package jason.app.weixin.social.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ANALYZE_RESULT")
public class AnalyzeResultImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column
	private Long userId;
    
    @Column(unique=true)
	private String keyStr;
    
    @Column
	private String type;
    
    @Column
	private Integer distance;
    
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="parent")
	private List<SeriesItemImpl> data;
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public List<SeriesItemImpl> getData() {
		return data;
	}

	public void setData(List<SeriesItemImpl> data) {
		this.data = data;
	}


    
}
