package jason.app.weixin.security.model;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class RememberMeAuthentication implements Authentication{

    /**
     * 
     */
    private static final long serialVersionUID = -7681039732368560472L;
	private PersistentRememberMeToken token;
    private Collection<? extends GrantedAuthority> authorities;
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }

    private boolean authenticated;
    private Object principal;
    private UserDetails details;
    private String name;
    public void setName(String name) {
        this.name = name;
    }

    public PersistentRememberMeToken getToken() {
        return token;
    }

    public void setToken(PersistentRememberMeToken token) {
        this.token = token;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return authorities;
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return "Protected";
    }

    @Override
    public Object getDetails() {
        // TODO Auto-generated method stub
        return details;
    }

    @Override
    public Object getPrincipal() {
        // TODO Auto-generated method stub
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        // TODO Auto-generated method stub
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        this.authenticated = isAuthenticated;
    }

}
