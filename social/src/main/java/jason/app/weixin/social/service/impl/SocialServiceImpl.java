package jason.app.weixin.social.service.impl;

import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.common.model.Category;
import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.social.constant.MessageType;
import jason.app.weixin.social.entity.AddFriendLinkImpl;
import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.entity.AnalyzeResultImpl;
import jason.app.weixin.social.entity.CommentImpl;
import jason.app.weixin.social.entity.MessageCommentImpl;
import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.entity.SettingsImpl;
import jason.app.weixin.social.entity.SnippetImpl;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialMailImpl;
import jason.app.weixin.social.entity.SocialMessageImpl;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.model.Comment;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.MessageComment;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.model.SocialDistance;
import jason.app.weixin.social.model.SocialMail;
import jason.app.weixin.social.model.SocialMessage;
import jason.app.weixin.social.model.SocialRelationDTO;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.AddFriendLinkRepository;
import jason.app.weixin.social.repository.AddFriendRequestRepository;
import jason.app.weixin.social.repository.AnalyzeResultRepository;
import jason.app.weixin.social.repository.CommentRepository;
import jason.app.weixin.social.repository.MessageCommentRepository;
import jason.app.weixin.social.repository.MessageRepository;
import jason.app.weixin.social.repository.SettingsRepository;
import jason.app.weixin.social.repository.SnippetRepository;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialMailRepository;
import jason.app.weixin.social.repository.SocialMessageRepository;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.social.translator.AddFriendRequestTranslator;
import jason.app.weixin.social.translator.AnalyzeResultTranslator;
import jason.app.weixin.social.translator.CommentTranslator;
import jason.app.weixin.social.translator.MessageCommentTranslator;
import jason.app.weixin.social.translator.MessageTranslator;
import jason.app.weixin.social.translator.SettingsTransaltor;
import jason.app.weixin.social.translator.SocialDistanceTranslator;
import jason.app.weixin.social.translator.SocialMailTranslator;
import jason.app.weixin.social.translator.SocialMessageTranslator;
import jason.app.weixin.social.translator.SocialUserTranslator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocialServiceImpl implements ISocialService {
	
	private static final Integer DEFAULT_DISTANCE = 3;

	private Logger logger = LoggerFactory.getLogger(SocialServiceImpl.class);
	
	@Autowired
	private AddFriendLinkRepository linkRepo;

	@Autowired
	private SocialUserRepository socialUserRepo;
	
	@Autowired
	private SocialDistanceRepository distanceRepo;
	
	@Autowired
	private SocialMessageRepository messageRepo;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private SettingsRepository settingsRepo;
	
	@Autowired
	private MessageRepository msgRepo;
	
	@Autowired
	private SocialRelationshipRepository relationRepo;
	
	@Autowired
	private AddFriendRequestRepository requestRepo;
	
	@Autowired
	private SocialMailRepository mailRepository;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private MessageCommentRepository msgCommentRepo;
	
	@Autowired
	private AnalyzeResultRepository analyzeResultRepo;
	
	@Autowired
	private SnippetRepository snippetRepo;
	
	@PersistenceContext(unitName="SocialPU")
	private EntityManager em;
	
	@Override
	@Transactional
	public void saveProfile(SocialUser profile) {
		// TODO Auto-generated method stub
		logger.info("save profile "+profile.getNickname()+"("+profile.getId()+")");
		SocialUserImpl user = socialUserRepo.findOne(profile.getId());
		if(user==null) {
			logger.info("user doesn't exist in db, create new");
			user = SocialUserTranslator.toEntity(profile);
		}else {
			
			if(profile.getAge()!=null)
			user.setAge(profile.getAge());
			if(profile.getNickname()!=null)
			user.setNickname(profile.getNickname());
			if(profile.getCategory1()!=null)
			user.setCategory1(profile.getCategory1());
			if(profile.getCategory2()!=null)
			user.setCategory2(profile.getCategory2());
			if(profile.getHobby()!=null)
			user.setHobbys(Arrays.toString(profile.getHobby()));
			if(profile.getLocation()!=null)
			user.setLocations(Arrays.toString(profile.getLocation()));
			if(profile.getSex()!=null)
			user.setSex(profile.getSex());
			if(profile.getCountry()!=null)
			user.setCountry(profile.getCountry());
			if(profile.getProvince()!=null)
			user.setProvince(profile.getProvince());
			if(profile.getCity()!=null)
			user.setCity(profile.getCity());
			if(profile.getHeadimgurl()!=null)
			user.setHeadimgurl(profile.getHeadimgurl());
			if(profile.getLanguage()!=null)
				user.setLanguage(profile.getLanguage());
				

			user.setLastUpdate(new Date());
		}
		logger.info("save user to database");
		logger.info(user.toString());
		user = socialUserRepo.save(user);
		logger.info("saved user to database");
		logger.info(user.toString());

	}

	@Override
	public SocialUser loadProfile(Long id) {
		// TODO Auto-generated method stub
		return SocialUserTranslator.toDTO(socialUserRepo.findOne(id));
	}

	@Override
	@Transactional
	public void postPersonalMessage(Long id, String title,String content,Long type) {
		// TODO Auto-generated method stub
//		List<SocialDistanceImpl> distances = distanceRepo.findByFromUser_IdAndDistanceLessThan(id,3);
		MessageImpl msg2 = new MessageImpl();
		SocialUserImpl su = socialUserRepo.findOne(id);
		msg2.setAuthor(su);
		msg2.setType(MessageType.PERSONAL);
		msg2.setTitle(title);
		msg2.setCategory(type);
		msg2.setContent(content);
		msg2.setLastUpdate(new Date());
		msg2 = msgRepo.save(msg2);
		//TODO transmit message in quartz job
/**		for(SocialDistanceImpl distance:distances) {
			SocialUserImpl user = distance.getToUser();
			SocialMessageImpl msg = new SocialMessageImpl();
			msg.setUser(user);
			msg.setMessage(msg2);
			messageRepo.save(msg);
		}
		*/
	}

	@Override
	public List<Message> getPersonalMessages(Long id,Long category,Pageable pageable) {
		// TODO Auto-generated method stub
		Page<SocialMessageImpl> messages = null;
		if(category==null) {
			messages = messageRepo.findByUser_Id(id,pageable);
		}else {
			messages = messageRepo.findByUser_IdAndMessage_Category(id,category,pageable);
		}
		List<Message> result = new ArrayList<Message>();
		for(SocialMessageImpl msg:messages) {
			Message message = MessageTranslator.toDTO(msg.getMessage());
			if(msg.getMessage().getCategory()==null) {
				Category cate = new Category(0L);
				cate.setName("未设定");
				message.setCategory(cate);
			}else {
				message.setCategory(categoryService.findById(msg.getMessage().getCategory()));
			}
			
			if(id==msg.getMessage().getAuthor().getId()) {
				SocialDistance dis = new SocialDistance();
				dis.setDistance(0);
				dis.setRating(5F);
				message.setSocialDistance(dis);
			}else {
				SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(id, msg.getMessage().getAuthor().getId());
				if(distance==null) {
					SocialDistance dis = new SocialDistance();
					dis.setRating(0F);
					dis.setDistance(999);
					message.setSocialDistance(dis);
				}else {
					message.setSocialDistance(SocialDistanceTranslator.toDTO(distance));
				}
			}
			message.setId(msg.getId());
			result.add(message);
		}
		return result;
	}

	@Override
	public Message getSocialMessage(Long userId,Long id) {
		// TODO Auto-generated method stub
		SocialMessageImpl msg = messageRepo.findOne(id);
		Message message = null;
		if(msg.getUser().getId().equals(userId)) {
			message = MessageTranslator.toDTO(msg.getMessage());
			if(message.getCategory()!=null) {
				message.setCategory(categoryService.findById(message.getCategory().getId()));
			}
			//message.setId(msg.getId());
			SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(userId, msg.getMessage().getAuthor().getId());
			if(distance!=null) {
				message.setSocialDistance(SocialDistanceTranslator.toDTO(distance));
			}
		}
		return message;
	}

	@Override
	public SocialDistance getSocialDistance(Long id, Long id2) {
		// TODO Auto-generated method stub
		SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(id,id2);
		if(distance!=null) return SocialDistanceTranslator.toDTO(distance);
		return null;
	}

	@Override
	public List<Message> getUserMessages(Long id,Pageable pageable) {
			// TODO Auto-generated method stub
			Page<MessageImpl> messages = msgRepo.findByAuthor_IdOrderByLastUpdateDesc(id,pageable);
//		List<MessageImpl> messages = msgRepo.findAll();
			List<Message> result = new ArrayList<Message>();
			for(MessageImpl msg:messages) {
				Message message = MessageTranslator.toDTO(msg);
			//	message.setCategory(categoryService.findById(msg.getCategory()));
				if(msg.getCategory()==null) {
					Category cate = new Category(0L);
					cate.setName("未设定");
					message.setCategory(cate);
				}else {
					message.setCategory(categoryService.findById(msg.getCategory()));
				}
				message.setDistance(0);	
				if(message.getSocialDistance()==null) {
					SocialDistance dis = new SocialDistance();
					dis.setRating(0F);
					message.setSocialDistance(dis);
				}
				result.add(message);
			}
			return result;
		}

	@Override
	public Message getMessage2(Long userId, Long id2) {
		MessageImpl msg = msgRepo.findOne(id2);
		Message message = null;
		if(msg.getAuthor().getId().equals(userId)) {
			message = MessageTranslator.toDTO(msg);
			message.setId(msg.getId());
			message.setDistance(0);
		}
		return message;
	}

	@Override
	public Settings getUserSettings(Long id) {
		// TODO Auto-generated method stub
		Settings settings = new Settings();
		SettingsImpl impl = settingsRepo.findOne(id);
		if(impl!=null) {
			settings = SettingsTransaltor.toDTO(impl);
		}
		return settings;
	}

	@Override
	public void saveSettings(Settings settings) {
		// TODO Auto-generated method stub
		SettingsImpl impl = SettingsTransaltor.toEntity(settings);
		settingsRepo.save(impl);
	}

	@Override
	public List<SocialUser> getFriends(Long user, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<SocialRelationshipImpl> users = relationRepo.findByFrom_Id(user,pageable);
		List<SocialUser> result = new ArrayList<SocialUser>();
		for(SocialRelationshipImpl dis:users) {
			result.add(SocialUserTranslator.toDTO(dis.getTo()));
		}
		
		return result;
	}

	@Override
	public List<SocialUser> getCircle(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		Settings settings = getUserSettings(id);
		Page<SocialDistanceImpl> distances = distanceRepo.findByFromUser_IdAndDistanceLessThanEqual(id, settings.getPersonalCircal(),pageable);
		List<SocialUser> result = new ArrayList<SocialUser>();
		for(SocialDistanceImpl dis:distances) {
			SocialUser user = SocialUserTranslator.toDTO(dis.getToUser());
			user.setDistance(dis.getDistance());
			result.add(user);
		}
		
		return result;
	}

	@Override
	public boolean isFriend(Long id, Long id2) {
		// TODO Auto-generated method stub
		return relationRepo.findOne(id+"_"+id2)!=null;
	}

	@Override
	public List<AddFriendRequest> getMyAddFriendRequests(Long id,
			Pageable pageable) {
		// TODO Auto-generated method stub
		Page<AddFriendRequestImpl> requests = requestRepo.findByTo_IdAndStatusIsNull(id,pageable);
		
		return AddFriendRequestTranslator.toDTO(requests);
	}

	@Override
	@Transactional
	public void saveDistance(SocialRelationDTO dto) {
		// TODO Auto-generated method stub
		SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(dto.getFrom(), dto.getTo());
		if(distance==null) {
			distance = new SocialDistanceImpl();
			distance.setFromUser(socialUserRepo.findOne(dto.getFrom()));
			distance.setToUser(socialUserRepo.findOne(dto.getTo()));
		}
		distance.setDistance(dto.getDistance());
		distanceRepo.save(distance);
	}

	@Override
	@Transactional
	public void createAddFriendLink(Long userId, String key) {
		// TODO Auto-generated method stub
		AddFriendLinkImpl link = new AddFriendLinkImpl();
		link.setUser(socialUserRepo.findOne(userId));
		link.setCreateDate(new Date());
		link.setId(key);
		link = linkRepo.save(link);
	}

	@Override
	public List<SocialMail> getUserConversation(List<Long> users,
			Pageable pageable) {
		
		return SocialMailTranslator.toDTO(mailRepository.findByFrom_IdInAndTo_IdInOrderByCreateDateDesc(users,users,pageable));
	}

	@Override
	public SocialUser loadProfileByOpenId(String openid) {
		// TODO Auto-generated method stub
		return SocialUserTranslator.toDTO(socialUserRepo.findByOpenid(openid));
		
	}

	@Override
	public void saveAnalyzeResult(AnalyzeResult result) {
		// TODO Auto-generated method stub
		AnalyzeResultImpl entity = AnalyzeResultTranslator.toEntity(result);
		 analyzeResultRepo.save(entity);
	}

	@Override
	public List<Comment> getUserComments(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return CommentTranslator.toDTO(commentRepo.findByTarget_IdOrderByCreateDateAsc(id,pageable));
	}

	@Override
	@Transactional
	public Comment saveComment(Comment comment) {
		// TODO Auto-generated method stub
		CommentImpl cmt = commentRepo.findByAuthor_IdAndTarget_Id(comment.getAuthor().getId(),comment.getTarget().getId());
		if(cmt==null) {
			cmt = CommentTranslator.toEntity(comment);
		}else {
			cmt.setMessage(comment.getMessage());
			cmt.setRating(comment.getRating());
		}
		
		cmt.setCreateDate(new Date());
		cmt = commentRepo.save(cmt);
		return CommentTranslator.toDTO(cmt);
	}

	@Override
	public SocialMail saveMail(SocialMail comment) {
		// TODO Auto-generated method stub
		SocialMailImpl mail = SocialMailTranslator.toEntity(comment);
		mail = mailRepository.save(mail);
		return SocialMailTranslator.toDTO(mail);
	}

	@Override
	public Float getUserRating(Long id) {
		// TODO Auto-generated method stub
		Float rating= commentRepo.getUserRating(id);
		if(rating==null) rating = 0F;
		return rating;
	}

	@Override
	public AnalyzeResult getAnalyzeResult(String key) {
		// TODO Auto-generated method stub
		AnalyzeResultImpl result = analyzeResultRepo.findByKeyStr(key);
		
		return AnalyzeResultTranslator.toDTO(result);
	}

	@Override
	public SocialUser findByExternalId(String fromUserName) {
		// TODO Auto-generated method stub
		return SocialUserTranslator.toDTO(socialUserRepo.findByOpenid(fromUserName));
	}

	@Override
	public Message getMessage(Long userId, Long id) {
		// TODO Auto-generated method stub
		return MessageTranslator.toDTO(msgRepo.findByIdAndAuthor_Id(id,userId));
	}

	@Override
	@Transactional
	public Message saveMessage(Message form) {
		// TODO Auto-generated method stub
		MessageImpl impl = MessageTranslator.toEntity(form);
		impl.setAuthor(socialUserRepo.findOne(form.getAuthor().getId()));
		impl.setLastUpdate(new Date());
		impl = msgRepo.save(impl);
		form.setId(impl.getId());
		
		return form;
	}

	@Override
	public SocialMessage publishMessageToUser(Long messageId, Long userId) {
		// TODO Auto-generated method stub
		logger.info("publish message "+messageId+" to "+userId);
		SocialMessageImpl msg = messageRepo.findByUser_IdAndMessage_Id(userId, messageId);
		if(msg==null) {
			msg = new SocialMessageImpl();
			msg.setMessage(msgRepo.findOne(messageId));
			msg.setUser(socialUserRepo.findOne(userId));
			msg.setCreateDate(new Date());
			msg = messageRepo.save(msg);
		}
		return SocialMessageTranslator.toDTO(msg);
	}

	@Override
	@Transactional
	public void publishMessage(Long messageId) {
		// TODO Auto-generated method stub
		MessageImpl msg = msgRepo.findOne(messageId);
		if(msg!=null) {
			Integer distance = msg.getTargetDistance();
			if(distance==null) {
				distance = DEFAULT_DISTANCE;
			}
			List<SocialUser> target = findTargetUsers(msg.getAuthor().getId(),msg.getTargetSex(),msg.getTargetMinAge(),msg.getTargetMaxAge(),distance,msg.getTargetRating());
			for(SocialUser usr:target) {
				publishMessageToUser(messageId, usr.getId());
			}
		}
	}

	private List<SocialUser> findTargetUsers(Long userId, Integer targetSex,
			Integer targetMinAge, Integer targetMaxAge, Integer targetDistance,
			Float targetRating) {
		// TODO Auto-generated method stub
		String queryStr = "select sd.toUser from SocialDistanceImpl sd where sd.fromUser.id =:userId and sd.distance < :distance ";
		Hashtable<String,Object> map = new Hashtable<String, Object>();
		map.put("userId", userId);
		map.put("distance", targetDistance);
		if(targetSex!=null) {
			queryStr = queryStr + " and sd.toUser.sex = :sex ";
			map.put("sex", targetSex);
		}
		if(targetMinAge!=null) {
			queryStr = queryStr + " and sd.toUser.age >= :minAge ";
			map.put("minAge", targetMinAge);
		}
		if(targetMaxAge!=null) {
			queryStr = queryStr + " and sd.toUser.age <= :maxAge ";
			map.put("maxAge", targetMaxAge);
		}
		if(targetRating!=null) {
			queryStr = queryStr + " and sd.rating >= :rating ";
			map.put("rating", targetRating);
		}
		Query query = em.createQuery(queryStr);
		for(Entry<String,Object> entry:map.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<SocialUserImpl> users =  query.getResultList();
		List<SocialUser> result = new ArrayList<SocialUser>();
		for(SocialUserImpl user:users) {
			result.add(SocialUserTranslator.toDTO(user));
		}
		return result;
	}

	@Override
	public List<MessageComment> getMessageComments(Long userId, Long id,
			Pageable pageable) {
		// TODO Auto-generated method stub
		List<MessageComment> comments =  MessageCommentTranslator.toDTO(msgCommentRepo.findByMessage_IdOrderByCreateDateDesc(id,pageable));
		for(MessageComment comment:comments) {
			if(userId==comment.getAuthor().getId()) {
				SocialDistance dis = new SocialDistance();
				dis.setDistance(0);
				dis.setRating(5F);
				comment.setDistance(dis);
			}else {
				SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(userId, comment.getAuthor().getId());
				if(distance==null) {
					SocialDistance dis = new SocialDistance();
					dis.setRating(0F);
					dis.setDistance(999);
					comment.setDistance(dis);
				}else {
					comment.setDistance(SocialDistanceTranslator.toDTO(distance));
				}
			}
		}
		return comments;
		
	}

	@Override
	@Transactional
	public MessageComment saveMessageComment(MessageComment comment) {
		// TODO Auto-generated method stub
		MessageCommentImpl impl = new MessageCommentImpl();
		impl.setAuthor(socialUserRepo.findOne(comment.getAuthor().getId()));
		impl.setContent(comment.getContent());
		impl.setCreateDate(new Date());
		impl.setMessage(msgRepo.findOne(comment.getMessage().getId()));
		if(comment.getReference()!=null) {
			impl.setReference(msgCommentRepo.findOne(comment.getReference().getId()));
		}
		impl = msgCommentRepo.save(impl);
		comment.setId(impl.getId());
		comment.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(impl.getCreateDate()));
		return comment;
	}

	@Override
	@Transactional
	public void saveSnippet(Long id, String content) {
		// TODO Auto-generated method stub
		SnippetImpl impl = new SnippetImpl();
		impl.setAuthor(em.find(SocialUserImpl.class, id));
		impl.setContent(content);
		snippetRepo.save(impl);
	}

}
