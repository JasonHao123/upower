package jason.app.weixin.security.translator;

import jason.app.weixin.security.model.AclInfo;

import java.util.ArrayList;
import java.util.List;

public class AclInfoTranslator {

    public static List<AclInfo> toAclInfoList(List<Object[]> result) {
        // TODO Auto-generated method stub
        List<AclInfo> list = new ArrayList<AclInfo>();
        for(Object[] acl:result) {
            AclInfo info = new AclInfo();
            info.setObjectIdIdentity((Long) acl[0]);
            info.setAceOrder((Integer) acl[1]);
            info.setAclId((Long) acl[2]);
            info.setParentObject((Long) acl[3]);
            info.setEntriesInheriting((Boolean) acl[4]);
            info.setAceId((Long) acl[5]);
            info.setMask((Integer) acl[6]);
            info.setGranting((Boolean) acl[7]);
            info.setAuditSuccess((Boolean) acl[8]);
            info.setAuditFailure((Boolean) acl[9]);
            info.setAcePrincipal((Boolean) acl[10]);
            info.setAceSid((String) acl[11]);
            info.setAclPrincipal((Boolean) acl[12]);
            info.setAclSid((String) acl[13]);
            info.setClazz((String) acl[14]);

            list.add(info);
        }
        return list;
    }

}
