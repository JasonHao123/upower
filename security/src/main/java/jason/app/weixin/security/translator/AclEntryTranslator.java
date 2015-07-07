package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.AclEntryImpl;
import jason.app.weixin.security.model.AclEntry;
import jason.app.weixin.security.model.AclObjectIdentity;

import java.util.ArrayList;
import java.util.List;

public class AclEntryTranslator {

	public static List<AclEntry> toDTO(List<AclEntryImpl> resultList,
			AclObjectIdentity identity) {
        List<AclEntry> result = new ArrayList<AclEntry>();
        if(resultList!=null) {
            for(AclEntryImpl aclObject:resultList) {
                result.add(toDTO(aclObject,identity));
            }
        }
        return result;
	}

	public static AclEntry toDTO(AclEntryImpl aclObject,
			AclObjectIdentity identity) {
        AclEntry entry = new AclEntry();
        entry.setAceOrder(aclObject.getAceOrder());
        entry.setAclObjectIdentity(identity);
        entry.setAuditFailure(aclObject.getAuditFailure());
        entry.setAuditSuccess(aclObject.getAuditSuccess());
        entry.setGranting(aclObject.getGranting());
        entry.setId(aclObject.getId());
        entry.setMask(aclObject.getMask());
        entry.setSid(AclSidTranslator.toDTO(aclObject.getSid()));
        return entry;
	}

	
}
