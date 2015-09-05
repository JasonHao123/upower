package jason.app.weixin.web.controller.circle.model;

import jason.app.weixin.social.constant.Status;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostMessageValidator implements Validator{

    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return clazz.equals(PostMessageForm.class);
    }

    public void validate(Object obj, Errors errors) {
    	PostMessageForm wc = (PostMessageForm) obj;

        if (StringUtils.isEmpty(wc.getTitle())) {
            errors.rejectValue("title", "validate.err.title", "标题至少三个字符. *");
        }
        if(Status.PUBLISHED==wc.getStatus()) {
        	if (StringUtils.isEmpty(wc.getContent())) {
                errors.rejectValue("content", "validate.err.content", " 发布内容不能为空. *");
            }
        }
 
    }

}
