package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.RoleImpl;
import jason.app.weixin.security.entity.UserImpl;
import jason.app.weixin.security.model.Role;
import jason.app.weixin.security.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserTranslator {

	public static User toDTO(UserImpl userImpl) {
		// TODO Auto-generated method stub
		if(userImpl==null) return null;
		User user = new User();
		user.setId(userImpl.getId());
		user.setEnabled(userImpl.isEnabled());
		user.setUsername(userImpl.getUsername());
		user.setExternalId(userImpl.getExternalId());
		user.setPassword("***[PROTECTED]***");
		if(userImpl.getRoles()!=null) {
			List<Role> roles = new ArrayList<Role>();
			for(RoleImpl roleImpl:userImpl.getRoles()) {
				Role role = new Role();
				role.setLabel(roleImpl.getLabel());
				role.setName(roleImpl.getName());
				roles.add(role);
			}
			user.setRoles(roles);
		}
		return user;
	}

}
