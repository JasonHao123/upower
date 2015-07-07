package jason.app.weixin.security.model;

public class AclInfo {
    private Long objectIdIdentity;
    private Integer aceOrder;
    private Long aclId;
    private Long parentObject;
    private Boolean entriesInheriting;
    private Long aceId;
    private Integer mask;
    private Boolean granting;
    private Boolean auditSuccess;
    private Boolean auditFailure;
    private Boolean acePrincipal;
    private String aceSid;
    private Boolean aclPrincipal;
    private String aclSid;
    private String clazz;
    public Long getObjectIdIdentity() {
        return objectIdIdentity;
    }
    public void setObjectIdIdentity(Long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }
    public Integer getAceOrder() {
        return aceOrder;
    }
    public void setAceOrder(Integer aceOrder) {
        this.aceOrder = aceOrder;
    }
    public Long getAclId() {
        return aclId;
    }
    public void setAclId(Long aclId) {
        this.aclId = aclId;
    }
    public Long getParentObject() {
        return parentObject;
    }
    public void setParentObject(Long parentObject) {
        this.parentObject = parentObject;
    }
    public Boolean getEntriesInheriting() {
        return entriesInheriting;
    }
    public void setEntriesInheriting(Boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }
    public Long getAceId() {
        return aceId;
    }
    public void setAceId(Long aceId) {
        this.aceId = aceId;
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
    public Boolean getAcePrincipal() {
        return acePrincipal;
    }
    public void setAcePrincipal(Boolean acePrincipal) {
        this.acePrincipal = acePrincipal;
    }
    public String getAceSid() {
        return aceSid;
    }
    public void setAceSid(String aceSid) {
        this.aceSid = aceSid;
    }
    public Boolean getAclPrincipal() {
        return aclPrincipal;
    }
    public void setAclPrincipal(Boolean aclPrincipal) {
        this.aclPrincipal = aclPrincipal;
    }
    public String getAclSid() {
        return aclSid;
    }
    public void setAclSid(String aclSid) {
        this.aclSid = aclSid;
    }
    public String getClazz() {
        return clazz;
    }
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
