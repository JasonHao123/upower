package jason.app.weixin.social.service.impl;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.social.constant.MessageType;
import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.entity.SettingsImpl;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialMessageImpl;
import jason.app.weixin.social.entity.SocialRelationshipImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.model.SocialRelationDTO;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.AddFriendRequestRepository;
import jason.app.weixin.social.repository.MessageRepository;
import jason.app.weixin.social.repository.SettingsRepository;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialMessageRepository;
import jason.app.weixin.social.repository.SocialRelationshipRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.social.translator.AddFriendRequestTranslator;
import jason.app.weixin.social.translator.MessageTranslator;
import jason.app.weixin.social.translator.SettingsTransaltor;
import jason.app.weixin.social.translator.SocialUserTranslator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocialServiceImpl implements ISocialService {

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
	
	@Override
	@Transactional
	public void saveProfile(SocialUser profile) {
		// TODO Auto-generated method stub
		SocialUserImpl user = SocialUserTranslator.toEntity(profile);
		socialUserRepo.save(user);
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
			message.setCategory(categoryService.findById(msg.getMessage().getCategory()));
			SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(id, msg.getMessage().getAuthor().getId());
			if(distance!=null) {
				message.setDistance(distance.getDistance());
			}
			message.setId(msg.getId());
			result.add(message);
		}
		return result;
	}

	@Override
	public Message getMessage(Long userId,Long id) {
		// TODO Auto-generated method stub
		SocialMessageImpl msg = messageRepo.findOne(id);
		Message message = null;
		if(msg.getUser().getId().equals(userId)) {
			message = MessageTranslator.toDTO(msg.getMessage());
			//message.setId(msg.getId());
			SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(userId, msg.getMessage().getAuthor().getId());
			if(distance!=null) {
				message.setDistance(distance.getDistance());
			}
		}
		return message;
	}

	@Override
	public Integer getSocialDistance(Long id, Long id2) {
		// TODO Auto-generated method stub
		SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(id,id2);
		if(distance!=null) return distance.getDistance();
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
				message.setCategory(categoryService.findById(msg.getCategory()));
				message.setDistance(0);				
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

}
