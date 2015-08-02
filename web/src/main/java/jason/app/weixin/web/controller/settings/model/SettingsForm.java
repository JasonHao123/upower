package jason.app.weixin.web.controller.settings.model;

public class SettingsForm {
	private Long id;
	private Long profileVisibility;

	public Long getProfileVisibility() {
		return profileVisibility;
	}

	public void setProfileVisibility(Long profileVisibility) {
		this.profileVisibility = profileVisibility;
	}

	private Integer personalCircal;

	private Integer groupCircle;
	
	private Long[] acceptMessageTypes;

	public Long[] getAcceptMessageTypes() {
		return acceptMessageTypes;
	}

	public void setAcceptMessageTypes(Long[] acceptMessageTypes) {
		this.acceptMessageTypes = acceptMessageTypes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
