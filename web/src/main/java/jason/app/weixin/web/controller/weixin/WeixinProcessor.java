package jason.app.weixin.web.controller.weixin;


import jason.app.weixin.web.controller.model.WeixinHeader;
import jason.app.weixin.web.controller.model.WeixinParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin")
public class WeixinProcessor
{

	private static Logger logger = LoggerFactory.getLogger(WeixinProcessor.class);
	@RequestMapping("/processor")
    public @ResponseBody String home(@ModelAttribute WeixinParam params,@ModelAttribute WeixinHeader header)
    {
		
		if(StringUtils.hasText(header.getEchostr())) {
			return header.getEchostr();
		}
		return "hello";
		/** 
		logger.info(header.getEchostr());
        WeixinParam response = new WeixinParam();
        response.setMsgType("text");
        response.setFromUserName(params.getToUserName());
        response.setCreateTime(params.getCreateTime());
        response.setToUserName(params.getFromUserName());       
        response.setContent("found more than one record, please provide more information");

        return response;
        */
    }


}
