package jason.app.weixin.security.model;

import java.util.List;

public class User {
	private Long id;
	private String externalId;
    public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	private String username;

    private String password;
    private boolean enabled;
    private List<Role> roles;
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return enabled;
    }

    public List<Role> getRoles() {
        // TODO Auto-generated method stub
        return roles;
    }


    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
