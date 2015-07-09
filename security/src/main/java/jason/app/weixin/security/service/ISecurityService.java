package jason.app.weixin.security.service;

import jason.app.weixin.security.exception.UserAlreadyExistException;
import jason.app.weixin.security.model.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface ISecurityService {
    public User createUser(String username,String password,List<String> roles) throws UserAlreadyExistException;

    public List<String> getAllUsernames();

    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password);

	public User getCurrentUser();
}
