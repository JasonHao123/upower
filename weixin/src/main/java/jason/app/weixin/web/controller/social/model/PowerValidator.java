package jason.app.weixin.web.controller.social.model;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PowerValidator implements Validator{

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return clazz.equals(PowerForm.class);
    }

    public void validate(Object obj, Errors errors) {
    	PowerForm wc = (PowerForm) obj;
    	if (wc.getDistance()==null) {
            errors.rejectValue("distance", "validate.err.username3", "请选择关系范围. *");
        }
        if (StringUtils.isEmpty(wc.getType())) {
            errors.rejectValue("type", "validate.err.username2", "请选择分析类别. *");
        }
 
    }

}
