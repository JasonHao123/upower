package jason.app.weixin.neo4j.controller;

import jason.app.weixin.common.model.CreateRelationCommand;
import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ExampleListener implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(ExampleListener.class);

	@Autowired
	private IWeixinService weixinService;
	
	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private INeo4jService neo4jService;
	
	@Transactional
    public void onMessage(Message message) {
		logger.info("on message"+message);
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }else if(message instanceof ObjectMessage) {
        	ObjectMessage hmsg = (ObjectMessage)message;
        	try {
				System.out.println(hmsg.getObject());
				Object object = hmsg.getObject();
				if(object instanceof CreateUserCommand) {
					CreateUserCommand command = (CreateUserCommand)object;
					SocialUser socialUser = null;
					if(command.getOpenId()!=null) {
						WeixinUser user = weixinService.getUserInfo(command.getOpenId());
						socialUser = new SocialUser();
				        socialUser.setNickname(user.getNickname());
				        socialUser.setSex(user.getSex());
				        socialUser.setCountry(user.getCountry());
				        socialUser.setProvince(user.getProvince());
				        socialUser.setCity(user.getCity());
				        socialUser.setHeadimgurl(user.getHeadimgurl());
				        socialUser.setOpenid(user.getOpenid());
						socialUser.setId(command.getUserId());
						socialService.saveProfile(socialUser);
					}else {
						socialUser = socialService.loadProfile(command.getUserId());
					}
					if(socialUser!=null) {
						neo4jService.createUser(socialUser);
					}
				}else if(object instanceof CreateRelationCommand) {
					CreateRelationCommand command = (CreateRelationCommand)object;
					neo4jService.createRelation(command.getFromUser(), command.getToUser(), command.getTypes(), command.getRating());
				}else if(object instanceof SendMessageCommand) {
					SendMessageCommand command = (SendMessageCommand)object;
					weixinService.postMessage(command);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }


}
