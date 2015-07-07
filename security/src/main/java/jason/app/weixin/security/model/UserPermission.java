package jason.app.weixin.security.model;

import org.springframework.security.acls.domain.AbstractPermission;

public class UserPermission extends AbstractPermission {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8545784189367062505L;

	protected UserPermission(int mask) {
        super(mask);
        // TODO Auto-generated constructor stub
    }

}
