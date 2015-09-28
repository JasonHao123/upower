package jason.app.weixin.neo4j.service.impl;

public class Item {
	public Item(String type,Long id) {
		this.type = type;
		this.id = id;
	}
	private String type;
	private Long id;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
