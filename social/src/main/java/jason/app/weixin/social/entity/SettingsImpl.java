package jason.app.weixin.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SETTINGS")
public class SettingsImpl {

	@Id
	private Long id;
	
	@Column
	private Long profileVisibility;
	
	@Column
	private Integer personalCircal;
	
	@Column
	private Integer groupCircle;
	
	@Column
	private String acceptMessageTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getProfileVisibility() {
		return profileVisibility;
	}

	public void setProfileVisibility(Long profileVisibility) {
		this.profileVisibility = profileVisibility;
	}

	public Integer getPersonalCircal() {
		return personalCircal;
	}

	public void setPersonalCircal(Integer personalCircal) {
		this.personalCircal = personalCircal;
	}

	public Integer getGroupCircle() {
		return groupCircle;
	}

	public void setGroupCircle(Integer groupCircle) {
		this.groupCircle = groupCircle;
	}

	public String getAcceptMessageTypes() {
		return acceptMessageTypes;
	}

	public void setAcceptMessageTypes(String acceptMessageTypes) {
		this.acceptMessageTypes = acceptMessageTypes;
	}
}
