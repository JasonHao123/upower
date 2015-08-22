package jason.app.weixin.web.controller.weixin;


import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;
import jason.app.weixin.web.service.EventHandlerChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeixinProcessor
{
  
	private static Logger logger = LoggerFactory.getLogger(WeixinProcessor.class);
	
	@Autowired
	private EventHandlerChain chain;
	
	//
	@RequestMapping(value="/processor",produces="application/xml")
    public @ResponseBody WeixinParam home(@RequestBody WeixinParam params,@ModelAttribute WeixinHeader header)
    {
		logger.info(params.toString());
		 return chain.handle(params,header);
		//return header.getEchostr();

    }



}
