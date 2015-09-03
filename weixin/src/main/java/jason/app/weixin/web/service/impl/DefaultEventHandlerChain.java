package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.controller.weixin.model.WeixinHeader;
import jason.app.weixin.web.controller.weixin.model.WeixinParam;
import jason.app.weixin.web.service.EventHandlerChain;
import jason.app.weixin.web.service.Handler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEventHandlerChain implements EventHandlerChain,InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(DefaultEventHandlerChain.class);

	@Autowired
	private List<Handler> handlers;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(handlers!=null) {
			Collections.sort(handlers,new Comparator<Handler>() {

				@Override
				public int compare(Handler o1, Handler o2) {
					// TODO Auto-generated method stub
					return new Integer(o1.getOrder()).compareTo(new Integer(o2.getOrder()));
				}
			});
		}
	}

	@Override
	public WeixinParam handle(WeixinParam params, WeixinHeader header) {
		// TODO Auto-generated method stub
		logger.info("fromUser:"+params.getFromUserName());
		logger.info("event:"+params.getEvent());
		logger.info("event key:"+params.getEventKey());
		logger.info("message type:"+params.getMsgType());
		logger.info("mediaId:"+params.getMediaId());
		for(Handler handler:handlers) {
			if(handler.canHandle(params, header)) {
				return handler.handle(params, header);
			}
		}
		return null;
	}
}
