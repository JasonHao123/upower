package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.AclClassImpl;
import jason.app.weixin.security.model.AclClass;

import java.util.ArrayList;
import java.util.List;

public class AclClassTranslator {

	public static List<AclClass> toDTO(List<AclClassImpl> resultList) {
        List<AclClass> result = new ArrayList<AclClass>();
        if(resultList!=null) {
            for(AclClassImpl aclObject:resultList) {
                result.add(toDTO(aclObject));
            }
        }
        return result;
	}

	public static AclClass toDTO(AclClassImpl clazz) {
        AclClass aclClass = new AclClass();
        aclClass.setClazz(clazz.getClazz());
        aclClass.setId(clazz.getId());
        return aclClass;
	}

}
