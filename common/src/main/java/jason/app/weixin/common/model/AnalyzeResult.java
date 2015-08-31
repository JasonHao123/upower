package jason.app.weixin.common.model;

import java.util.List;

public class AnalyzeResult {
	private Long id;
	private Long userId;
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private String key;
	private String type;
	private Integer distance;
	private List<SeriesItem> data;
	
	public List<SeriesItem> getData() {
		return data;
	}

	public void setData(List<SeriesItem> data) {
		this.data = data;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	
	
}
