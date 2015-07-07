package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.entity.RoleImpl;
import jason.app.weixin.security.entity.UserImpl;
import jason.app.weixin.security.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@Service("userDetailService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;
        if (StringUtils.hasText(username)) {
            UserImpl dbUser = null;
            try {
                dbUser = userDao.findByUsername(username);
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
//                if(roles.size()==0) {
//                    roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//                }
                user = new org.springframework.security.core.userdetails.User(username, dbUser.getPassword(), dbUser.isEnabled(), true, true, true, roles);
            }
        }
        if(user==null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }

}
