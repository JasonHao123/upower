package jason.app.weixin.social.api.model;

import java.io.Serializable;

public class SocialRelationDTO implements Serializable{
	private Long from;
	private Long to;
	private Integer distance;
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
}
