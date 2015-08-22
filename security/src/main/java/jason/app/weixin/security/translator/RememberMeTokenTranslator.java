package jason.app.weixin.security.translator;

import jason.app.weixin.security.entity.RememberMeTokenImpl;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class RememberMeTokenTranslator {

	public static RememberMeTokenImpl toEntity(PersistentRememberMeToken token) {
		// TODO Auto-generated method stub
		RememberMeTokenImpl impl = new RememberMeTokenImpl();
		impl.setSeries(token.getSeries());
		impl.setUsername(token.getUsername());
		impl.setToken(token.getTokenValue());
		impl.setLastUsed(token.getDate());
		return impl;
	}

	public static PersistentRememberMeToken toDTO(RememberMeTokenImpl token) {
		// TODO Auto-generated method stub
		if(token!=null) {
			return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
		}
		return null;
	}

}
