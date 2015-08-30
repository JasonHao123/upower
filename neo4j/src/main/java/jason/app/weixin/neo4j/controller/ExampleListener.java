package jason.app.weixin.neo4j.controller;

import jason.app.weixin.common.model.AnalyzeRelationCommand;
import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.common.model.CreateRelationCommand;
import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.Text;
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
import org.springframework.util.StringUtils;

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
					handleCreateUser(object);
				}else if(object instanceof CreateRelationCommand) {
					CreateRelationCommand command = (CreateRelationCommand)object;
					neo4jService.createRelation(command.getFromUser(), command.getToUser(), command.getTypes(), command.getRating());
				}else if(object instanceof SendMessageCommand) {
					SendMessageCommand command = (SendMessageCommand)object;
					weixinService.postMessage(command);
				}else if(object instanceof AnalyzeRelationCommand) {
					handleAnalyzeRelation(object);
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

	private void handleAnalyzeRelation(Object object) {
		// TODO Auto-generated method stub
		AnalyzeRelationCommand command = (AnalyzeRelationCommand)object;
		/**		SocialUser  user = socialService.loadProfileByOpenId(command.getOpenid());
		AnalyzeResult result = neo4jService.analyze(user.getId());
		result = socialService.saveAnalyzeResult(result);
		*/
		SendMessageCommand message = new SendMessageCommand();
		message.setMsgtype("text");
		message.setText(new Text("点击以下链接查看分析结果<a href=\"http://www.weaktie.cn/weixin/public/result.do?id=1\">查看</a>"));
		message.setTouser(command.getOpenid());
		weixinService.postMessage(message);
	}

	private void handleCreateUser(Object object) {
		CreateUserCommand command = (CreateUserCommand)object;
		SocialUser socialUser = null;
		if(command.getOpenId()!=null) {
			WeixinUser user = weixinService.getUserInfo(command.getOpenId());
			if(user!=null && StringUtils.isEmpty(user.getErrcode())) {
				socialUser = new SocialUser();
		        socialUser.setNickname(user.getNickname());
		        socialUser.setSex(user.getSex());
		        socialUser.setCountry(user.getCountry());
		        socialUser.setProvince(user.getProvince());
		        socialUser.setCity(user.getCity());
		        socialUser.setHeadimgurl(user.getHeadimgurl());
		        socialUser.setOpenid(user.getOpenid());
		        socialUser.setLanguage(user.getLanguage());
				socialUser.setId(command.getUserId());
				socialService.saveProfile(socialUser);
			}else if(user!=null && StringUtils.hasText(user.getErrcode())) {
				logger.info("error to fetching user info "+user.getErrcode());
				user = null;
			}
		}else {
			socialUser = socialService.loadProfile(command.getUserId());
		}
		if(socialUser!=null) {
			neo4jService.createUser(socialUser);
			if(command.getOpenId()!=null) {
				SendMessageCommand command2 = new SendMessageCommand();
				command2.setMsgtype("text");
				command2.setTouser(command.getOpenId());
				command2.setText(new Text("完善您的个人信息有利于优化您的个人关系网络，<a href=\"http://www.weaktie.cn/weixin/social/profile/edit.do\">请点击此链接编辑个人信息</a>，别忘了邀请好友加入啊，点击社交－>邀请好友"));
				weixinService.postMessage(command2);
			}						
		}
	}


}
