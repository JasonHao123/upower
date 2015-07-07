package jason.app.weixin.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ACL_ENTRY")
public class AclEntryImpl{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="ACL_OBJECT_IDENTITY")
    private AclObjectIdentityImpl aclObjectIdentity;
    
    @Column(name="ACE_ORDER")
    private Integer aceOrder;
    
    @ManyToOne
    @JoinColumn(name="SID")
    private AclSidImpl sid;
    
    @Column(name="MASK")
    private Integer mask;
    
    @Column(name="GRANTING")
    private Boolean granting;
    
    @Column(name="AUDIT_SUCCESS")
    private Boolean auditSuccess;
  
    @Column(name="AUDIT_FAILURE")
    private Boolean auditFailure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclObjectIdentityImpl getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(AclObjectIdentityImpl aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public Integer getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }

    public AclSidImpl getSid() {
        return sid;
    }

    public void setSid(AclSidImpl sid) {
        this.sid = sid;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public Boolean getGranting() {
        return granting;
    }

    public void setGranting(Boolean granting) {
        this.granting = granting;
    }

    public Boolean getAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(Boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    public Boolean getAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(Boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

}
