package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SERIES_ITEM")
public class SeriesItemImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @ManyToOne
    private AnalyzeResultImpl parent;
    
    @Column
	private String series;
    
    @Column
	private int order1;
    
    @Column
	private String keyStr;
    
    public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	@Column
	private Double value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnalyzeResultImpl getParent() {
		return parent;
	}

	public void setParent(AnalyzeResultImpl parent) {
		this.parent = parent;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public int getOrder1() {
		return order1;
	}

	public void setOrder1(int order) {
		this.order1 = order;
	}


	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
    
    
}
