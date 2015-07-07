package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.AclSidImpl;
import jason.app.weixin.security.model.AclSid;

public class AclSidTranslator {

	public static AclSid toDTO(AclSidImpl sid2) {
        AclSid sid = new AclSid();
        sid.setId(sid2.getId());
        sid.setPrincipal(sid2.getPrincipal());
        sid.setSid(sid2.getSid());
        return sid;
	}

}
