package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.entity.RememberMeTokenImpl;
import jason.app.weixin.security.repository.RememberTokenRepository;
import jason.app.weixin.security.translator.RememberMeTokenTranslator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository("tokenRepo")
public class DaoPersistentTokenRepository implements PersistentTokenRepository {

	@Autowired
	private RememberTokenRepository tokenRepo;
	
	@Override
	@Transactional
	public void createNewToken(PersistentRememberMeToken token) {
		// TODO Auto-generated method stub
		RememberMeTokenImpl impl = RememberMeTokenTranslator.toEntity(token);
		tokenRepo.save(impl);
	}

	@Override
	@Transactional
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		// TODO Auto-generated method stub
		RememberMeTokenImpl impl = tokenRepo.findOne(series);
		if(impl!=null) {
			impl.setToken(tokenValue);
			impl.setLastUsed(lastUsed);
			tokenRepo.save(impl);
		}
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		// TODO Auto-generated method stub
		return RememberMeTokenTranslator.toDTO(tokenRepo.findOne(seriesId));
	}

	@Override
	public void removeUserTokens(String username) {
		// TODO Auto-generated method stub

	}

}
