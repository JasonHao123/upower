package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.entity.RoleImpl;
import jason.app.weixin.security.entity.UserImpl;
import jason.app.weixin.security.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
@Service("oauthUserDetailService")
public class OauthUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;
        if (StringUtils.hasText(username)) {
            UserImpl dbUser = null;
            try {
                dbUser = userDao.findByExternalId(username);
            }catch(Exception e) {
                e.printStackTrace();
            }
            if (dbUser != null) {
                List roles = new ArrayList<SimpleGrantedAuthority>();
                if(dbUser.getRoles()!=null) {
                    for(RoleImpl role:dbUser.getRoles()) {
                        roles.add(new SimpleGrantedAuthority(role.getName()));
                    }
                }
                String password = passwordEncoder.encode(DigestUtils.md5Hex(username));
                user = new org.springframework.security.core.userdetails.User(dbUser.getUsername(), password, dbUser.isEnabled(), true, true, true, roles);
            }
        }
        if(user==null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }

}
