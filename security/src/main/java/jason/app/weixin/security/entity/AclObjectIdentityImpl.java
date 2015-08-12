package jason.app.weixin.security.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.acls.model.ObjectIdentity;

@Entity
@Table(name="ACL_OBJECT_IDENTITY")
public class AclObjectIdentityImpl implements ObjectIdentity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="OBJECT_ID_CLASS")
    private AclClassImpl objIdClass;
    
    @Column(name="OBJECT_ID_IDENTITY")
    private Long objIdIdentity;
    
    @ManyToOne
    @JoinColumn(name="PARENT_OBJECT")
    private AclObjectIdentityImpl parentObject;
    
    @ManyToOne
    @JoinColumn(name="OWNER_SID")
    private AclSidImpl owner;
    
    @Column(name="ENTRIES_INHERITING")
    private Boolean entriesInheriting;
    
    @OneToMany(mappedBy="aclObjectIdentity",fetch=FetchType.EAGER)
    private List<AclEntryImpl> aclEntries;

    public List<AclEntryImpl> getAclEntries() {
        return aclEntries;
    }

    public void setAclEntries(List<AclEntryImpl> aclEntries) {
        this.aclEntries = aclEntries;
    }

    public List<AclEntryImpl> getEntries() {
        if(aclEntries!=null) {
            return aclEntries;
        }
        return null;
    }

    public void setEntries(List<AclEntryImpl> entries) {
        this.aclEntries = entries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclClassImpl getObjIdClass() {
        return objIdClass;
    }

    public void setObjIdClass(AclClassImpl objIdClass) {
        this.objIdClass = objIdClass;
    }

    public Long getObjIdIdentity() {
        return objIdIdentity;
    }

    public void setObjIdIdentity(Long objIdIdentity) {
        this.objIdIdentity = objIdIdentity;
    }

    public AclObjectIdentityImpl getParentObject() {
        return parentObject;
    }

    public void setParentObject(AclObjectIdentityImpl parentObject) {
        this.parentObject = parentObject;
    }

    public AclSidImpl getOwner() {
        return owner;
    }

    public void setOwner(AclSidImpl owner) {
        this.owner = owner;
    }

    public Boolean getEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(Boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

	@Override
	@Transient
	public Serializable getIdentifier() {
		// TODO Auto-generated method stub
		return this.objIdIdentity;
	}

	@Override
	@Transient
	public String getType() {
		// TODO Auto-generated method stub
	       return objIdClass.getClazz();
	}

}
