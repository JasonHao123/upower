package jason.app.weixin.web.controller.social.model;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProfileValidator implements Validator{

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return clazz.equals(ProfileForm.class);
    }

    public void validate(Object obj, Errors errors) {
    	ProfileForm wc = (ProfileForm) obj;

        if (StringUtils.isEmpty(wc.getNickname())) {
            errors.rejectValue("nickname", "validate.err.username", "Please input a valid username. *");
        }
 
    }

}
