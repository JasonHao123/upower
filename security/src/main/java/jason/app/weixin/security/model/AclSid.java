package jason.app.weixin.security.model;

import org.springframework.security.acls.model.Sid;


public class AclSid implements Sid{

    /**
	 * 
	 */
	private static final long serialVersionUID = -261084439425209959L;

	private Long id;

    private Boolean principal;

    private String sid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }   
           
}
