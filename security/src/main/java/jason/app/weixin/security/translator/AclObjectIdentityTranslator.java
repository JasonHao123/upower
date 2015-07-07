package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.AclObjectIdentityImpl;
import jason.app.weixin.security.model.AclObjectIdentity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.acls.model.ObjectIdentity;

public class AclObjectIdentityTranslator {

	public static List<ObjectIdentity> toDTO(List<AclObjectIdentityImpl> resultList) {
		// TODO Auto-generated method stub
        List<ObjectIdentity> result = new ArrayList<ObjectIdentity>();
        if(resultList!=null) {
            for(AclObjectIdentityImpl aclObject:resultList) {
                result.add(toDTO(aclObject));
            }
        }
        return result;
	}


    public static AclObjectIdentity toDTO(AclObjectIdentityImpl aclObject) {
        if(aclObject==null) return null;
        // TODO Auto-generated method stub
        AclObjectIdentity identity = new AclObjectIdentity();
        identity.setId(aclObject.getId());
        identity.setAclEntries(AclEntryTranslator.toDTO(aclObject.getAclEntries(),identity));
        identity.setEntriesInheriting(aclObject.getEntriesInheriting());
        identity.setObjIdClass(AclClassTranslator.toDTO(aclObject.getObjIdClass()));
        identity.setObjIdIdentity(aclObject.getObjIdIdentity());
        identity.setOwner(AclSidTranslator.toDTO(aclObject.getOwner()));
        identity.setParentObject(toDTO(aclObject.getParentObject()));

        return identity;
    }
}
