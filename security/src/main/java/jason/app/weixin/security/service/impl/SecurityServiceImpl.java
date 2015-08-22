package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.entity.RoleImpl;
import jason.app.weixin.security.entity.UserImpl;
import jason.app.weixin.security.exception.UserAlreadyExistException;
import jason.app.weixin.security.model.User;
import jason.app.weixin.security.repository.RoleRepository;
import jason.app.weixin.security.repository.UserRepository;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.security.translator.UserTranslator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("securityService")
public class SecurityServiceImpl implements ISecurityService {
    @Autowired
    private UserRepository userDao;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private List<AuthenticationProvider> authenticationProvider;
    
    @Autowired(required=false)
    private RememberMeServices rememberService;

    @Override
    @Transactional
    public User createUser(String username, String password, List<String> roles) throws UserAlreadyExistException{
        // TODO Auto-generated method stub
    	UserImpl userImpl = new UserImpl();
    	userImpl.setEnabled(true);
    	userImpl.setUsername(username);
    	userImpl.setPassword(encoder.encode(password));
    	List<RoleImpl> roleImpls = new ArrayList<RoleImpl>();
    	// save first time to generate id, otherwise roles cannot be added
    	userImpl = userDao.save(userImpl);
    	
    	if(roles!=null) {
    		for(String role:roles) {
    			RoleImpl roleImpl = findOrCreateRole(role);
    			roleImpls.add(roleImpl);
    		}
    	}
    	userImpl.setRoles(roleImpls);
    	userImpl = userDao.save(userImpl);
        return UserTranslator.toDTO(userImpl);
    }
    @Transactional
	private RoleImpl findOrCreateRole(String role) {
		
		RoleImpl roleImpl = roleRepo.findOne(role);
		if(roleImpl==null) {
			roleImpl = new RoleImpl();
			roleImpl.setName(role);
			roleImpl.setLabel(role);
			roleImpl = roleRepo.save(roleImpl);
		}
		return roleImpl;
	}
    @Override
    public List<String> getAllUsernames() {
        // TODO Auto-generated method stub
    	List<String> users = new ArrayList<String>();
    	List<UserImpl> result = userDao.findAll();
    	for(UserImpl impl:result) {
    		users.add(impl.getUsername());
    	}
        return users;
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        // TODO Auto-generated method stub
        try {
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            token.setDetails(new WebAuthenticationDetails(request));
            for(AuthenticationProvider provider:authenticationProvider) {
            	if(provider.supports(UsernamePasswordAuthenticationToken.class)) {
		            Authentication authentication = provider.authenticate(token);
		            if(authentication.isAuthenticated()) {
			            SecurityContextHolder.getContext().setAuthentication(authentication);
			            rememberService.loginSuccess(request, response, authentication);
			            break;
		            }
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
	@Override
	public User getCurrentUser() {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = UserTranslator.toDTO(userDao.findByUsername(username));
		return user;
	}
	@Override
	public boolean isWeixinUserExists(String openid) {
		// TODO Auto-generated method stub
		UserImpl user = userDao.findByExternalId(openid);
		return user!=null;
	}
	@Override
	@Transactional
	public User createExternalUser(String username, String password,
			List<String> roles) {
		// TODO Auto-generated method stub
	  	UserImpl userImpl = new UserImpl();
    	userImpl.setEnabled(true);
    	userImpl.setUsername(username);
    	userImpl.setExternalId(username);
    	userImpl.setPassword(encoder.encode(password));
    	List<RoleImpl> roleImpls = new ArrayList<RoleImpl>();
    	// save first time to generate id, otherwise roles cannot be added
    	userImpl = userDao.save(userImpl);
    	
    	if(roles!=null) {
    		for(String role:roles) {
    			RoleImpl roleImpl = findOrCreateRole(role);
    			roleImpls.add(roleImpl);
    		}
    	}
    	userImpl.setRoles(roleImpls);
    	userImpl = userDao.save(userImpl);
        return UserTranslator.toDTO(userImpl);
	}
	@Override
	public boolean loginExternalUser(HttpServletRequest request,
			HttpServletResponse resp, String openid) {
		// TODO Auto-generated method stub
        try {
            // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(openid, DigestUtils.md5Hex(openid));
            token.setDetails(new WebAuthenticationDetails(request));

            for(AuthenticationProvider provider:authenticationProvider) {
            	if(provider.supports(UsernamePasswordAuthenticationToken.class)) {
		            Authentication authentication = provider.authenticate(token);
		            if(authentication.isAuthenticated()) {
			            SecurityContextHolder.getContext().setAuthentication(authentication);
			            rememberService.loginSuccess(request, resp, authentication);
			            return true;
		            }
            	}
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            SecurityContextHolder.getContext().setAuthentication(null);
            return false;
        }
	}
	@Override
	public User findExternalUser(String openid) {
		// TODO Auto-generated method stub
		UserImpl user = userDao.findByExternalId(openid);
		return UserTranslator.toDTO(user);
	}
	@Override
	public void enableUser(Long id,boolean status) {
		// TODO Auto-generated method stub
		UserImpl user = userDao.findOne(id);
		if(user!=null) {
			user.setEnabled(status);
			userDao.save(user);
		}
	}

}
