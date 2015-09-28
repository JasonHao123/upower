package jason.app.weixin.neo4j.controller;

import jason.app.weixin.common.constant.MediaType;
import jason.app.weixin.common.model.AnalyzeRelationCommand;
import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.model.CreateRelationCommand;
import jason.app.weixin.common.model.CreateUserCommand;
import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;
import jason.app.weixin.common.model.LinkMessageCommand;
import jason.app.weixin.common.model.PublishMessageCommand;
import jason.app.weixin.common.model.SaveMediaCommand;
import jason.app.weixin.common.model.SendMessageCommand;
import jason.app.weixin.common.model.SnippetMessageCommand;
import jason.app.weixin.common.model.TagMessageCommand;
import jason.app.weixin.common.model.Text;
import jason.app.weixin.common.model.WeixinUser;
import jason.app.weixin.common.service.IAmazonS3Service;
import jason.app.weixin.common.service.IFileService;
import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.neo4j.service.impl.CacheUtil;
import jason.app.weixin.neo4j.service.impl.Crawler;
import jason.app.weixin.neo4j.service.impl.Item;
import jason.app.weixin.social.entity.SnippetImpl;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	@Autowired
	private IAmazonS3Service s3Service;
	
	@Autowired
	private IFileService fileService;
	
	@Inject
	private CacheUtil cacheUtil;
	
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
				}else if(object instanceof SaveMediaCommand) {
					handleSaveMedia(object);
				}else if(object instanceof PublishMessageCommand) {
					handlePublishMessage(object);
				}else if(object instanceof LinkMessageCommand ) {
					handleLinkMessage(object);
				}else if(object instanceof SnippetMessageCommand ) {
					handleSnippetMessage(object);
				}else if(object instanceof TagMessageCommand) {
					handleTagMessage(object);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }

	private void handleTagMessage(Object object) {
		// TODO Auto-generated method stub
		
	}

	private void handleSnippetMessage(Object object) {
		// TODO Auto-generated method stub
		SnippetMessageCommand command = (SnippetMessageCommand)object;		
		SocialUser user = socialService.findByExternalId(command.getOpenId());
		socialService.saveSnippet(user.getId(),command.getContent());
	//	snippet.setAuthor(author);
	}

	private void handleLinkMessage(Object object) {
		// TODO Auto-generated method stub
		LinkMessageCommand lmc = (LinkMessageCommand)object;
		SocialUser user = socialService.findByExternalId(lmc.getOpenid());
		jason.app.weixin.social.model.Message message = new jason.app.weixin.social.model.Message();
		message.setAuthor(user);
//		message.setStatus(Status.PUBLISHED);
		message.setCategory(new Category(79L));
	//	message.setTitle(lmc.getTitle());
	//	message.setLink(command.getUrl());
		Crawler.fetch(lmc.getUrl(),message);
		message = socialService.saveMessage(message);
		cacheUtil.cacheItem(new Item("message",message.getId()));
		try {
			sendMessage(lmc.getOpenid(),"请点击以下链接编辑消息并发布。<a href=\"http://www.weaktie.cn/weixin/circle/post.do?id="+message.getId()+"\">编辑</a>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new RuntimeException(e);
		}
		
	}

	private void handlePublishMessage(Object object) {
		// TODO Auto-generated method stub
		logger.info("publish message "+object);
		PublishMessageCommand command = (PublishMessageCommand)object;
		if(command.getMessageId()!=null) {
			/// socialService.publishMessage(command.getMessageId());
		}else if(command.getOpenid()!=null) {
			SocialUser user = socialService.findByExternalId(command.getOpenid());
			jason.app.weixin.social.model.Message message = new jason.app.weixin.social.model.Message();
			message.setAuthor(user);
//			message.setStatus(Status.PUBLISHED);
			message.setCategory(new Category(79L));
			message.setTitle(command.getTitle());
			message.setLink(command.getUrl());
			message.setCategory(new Category(79L));
			message = socialService.saveMessage(message);
			try {
				sendMessage(command.getOpenid(),"请点击以下链接编辑消息并发布。<a href=\"http://www.weaktie.cn/weixin/circle/post.do?id="+message.getId()+"\">编辑</a>");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				new RuntimeException(e);
			}
		}
	}

	private void handleSaveMedia(Object object) throws Exception{
		// TODO Auto-generated method stub
		FileInfo media = null;
		FileInfo thumbnail = null;
		try {
			SaveMediaCommand command = (SaveMediaCommand)object;
			media = weixinService.downloadMedia(command.getMediaId());
			media.setMediaType(command.getMediaType());
			SocialUser user = socialService.findByExternalId(command.getOpenid());
			
			if(command.getThumbnailId()!=null) {
				thumbnail = weixinService.downloadMedia(command.getThumbnailId());
				thumbnail.setMediaType(command.getMediaType());
			}
			if(thumbnail==null && MediaType.IMAGE==command.getMediaType()) {
				thumbnail = fileService.createThumbnail(media);
			}
			
			String mediaUrl = s3Service.saveFile(media);
			logger.info("media url "+mediaUrl);
			String thumbnailUrl =null;
			if(thumbnail!=null) {
				thumbnailUrl = s3Service.saveFile(thumbnail);
			}
			logger.info("thumbnail url "+mediaUrl);
			FileItem file = new FileItem();
			file.setUrl(mediaUrl);
			file.setThumbnail(thumbnailUrl);
			file.setCreateDate(new Date());
			file.setUserId(user.getId());
			file.setContentType(media.getContentType());
			file.setMediaType(command.getMediaType());
			fileService.saveFile(file);
			cacheUtil.cacheItem(new Item("media",file.getId()));
		}finally {
			if(media!=null && media.getFile()!=null) {
				media.getFile().delete();
			}
			if(thumbnail!=null && thumbnail.getFile()!=null) {
				thumbnail.getFile().delete();
			}
		}
	}

	private void handleAnalyzeRelation(Object object) throws Exception {
		// TODO Auto-generated method stub
		String message = null;
		AnalyzeRelationCommand command = (AnalyzeRelationCommand)object;
		SocialUser  user = socialService.loadProfileByOpenId(command.getOpenid());	
		AnalyzeResult result = null;
		if("SOCIAL".equals(command.getType()) || "NETWORK".equals(command.getType())) {
			message = "关系网络不完善，无法进行分析，请邀请更多好友加入，或等待好友邀请更多人加入，谢谢！";
		}else if("SEX".equals(command.getType())){
			 result = neo4jService.analyzeSexAndAge(user.getId(),command.getDistance());
			 message = "性别年龄关系范围"+command.getDistance()+"分析结果<a href=\"http://www.weaktie.cn/weixin/public/result.do?key=%s\">查看</a>";
		}else if("LOCATION".equals(command.getType())){
			 result = neo4jService.analyzeLocation(user.getId(),command.getDistance());	
			 message = "地区关系范围"+command.getDistance()+"分析结果<a href=\"http://www.weaktie.cn/weixin/public/result.do?key=%s\">查看</a>";
		}else if("PROFESSION".equals(command.getType())){
			// result = neo4jService.analyzeProfession(user.getId(),command.getDistance()); 
			message = "程序猿努力开发中，敬请期待！";
		}
		if(result!=null) {
			result.setUserId(user.getId());
			result.setDistance(command.getDistance());
			result.setType(command.getType());
			for(int i=0;i<3;i++) {
				try {
					result.setKey(generateId());
					socialService.saveAnalyzeResult(result);
					break;
				}catch(DataAccessException e) {
					e.printStackTrace();
				}
			}
			if(message!=null) {
				message = String.format(message, result.getKey());
			}
		}
		/**		SocialUser  user = socialService.loadProfileByOpenId(command.getOpenid());
		AnalyzeResult result = neo4jService.analyze(user.getId());
		result = socialService.saveAnalyzeResult(result);
		*/
		logger.info("handle command "+command.getOpenid()+" "+command.getType()+ " "+command.getDistance());
		if(message!=null) {
			sendMessage(command.getOpenid(),message);
		}
		
		///"点击以下链接查看分析结果<a href=\"http://www.weaktie.cn/weixin/public/result.do?id=1\">查看</a>"
	}

	private String generateId() {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		return DigestUtils.md5Hex(uuid.toString());
	}

	private void sendMessage(String openid, String text) throws Exception {
		SendMessageCommand message = new SendMessageCommand();
		message.setMsgtype("text");
		message.setText(new Text(text));
		message.setTouser(openid);
		weixinService.postMessage(message);
	}

	private void handleCreateUser(Object object) throws Exception {
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
				command2.setText(new Text("完善您的个人信息有利于优化您的个人关系网络，请点击此链接<a href=\"http://www.weaktie.cn/weixin/social/profile/edit.do\">编辑个人信息</a>，别忘了邀请好友加入啊，<a href=\"http://www.weaktie.cn/weixin/social/invite.do\">邀请</a>"));
				weixinService.postMessage(command2);
			}						
		}
	}


}
