package jason.app.weixin.security.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.acls.model.ObjectIdentity;

public class AclObjectIdentity implements ObjectIdentity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7409196152221551188L;

	private Long id;

    private AclClass objIdClass;

    private Long objIdIdentity;

    private AclObjectIdentity parentObject;

    private AclSid owner;

    private Boolean entriesInheriting;

    private List<AclEntry> aclEntries;

    public List<AclEntry> getAclEntries() {
        return aclEntries;
    }

    public void setAclEntries(List<AclEntry> aclEntries) {
        this.aclEntries = aclEntries;
    }

    public List<AclEntry> getEntries() {
        if(aclEntries!=null) {
            return aclEntries;
        }
        return null;
    }

    public void setEntries(List<AclEntry> entries) {
        this.aclEntries = entries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclClass getObjIdClass() {
        return objIdClass;
    }

    public void setObjIdClass(AclClass objIdClass) {
        this.objIdClass = objIdClass;
    }

    public Long getObjIdIdentity() {
        return objIdIdentity;
    }

    public void setObjIdIdentity(Long objIdIdentity) {
        this.objIdIdentity = objIdIdentity;
    }

    public AclObjectIdentity getParentObject() {
        return parentObject;
    }

    public void setParentObject(AclObjectIdentity parentObject) {
        this.parentObject = parentObject;
    }

    public AclSid getOwner() {
        return owner;
    }

    public void setOwner(AclSid owner) {
        this.owner = owner;
    }

    public Boolean getEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(Boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

    @Override
    public Serializable getIdentifier() {
        
        return this.objIdIdentity;
    }

    @Override
    public String getType() {

        return objIdClass.getClazz();
    }

}
