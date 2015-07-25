package jason.app.weixin.social.service.impl;

import jason.app.weixin.common.service.ICategoryService;
import jason.app.weixin.social.constant.MessageType;
import jason.app.weixin.social.entity.MessageImpl;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialMessageImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.Message;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.repository.MessageRepository;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialMessageRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.service.ISocialService;
import jason.app.weixin.social.translator.MessageTranslator;
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
	private MessageRepository msgRepo;
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
		List<SocialDistanceImpl> distances = distanceRepo.findByFromUser_IdAndDistanceLessThan(id,3);
		MessageImpl msg2 = new MessageImpl();
		SocialUserImpl su = socialUserRepo.findOne(id);
		msg2.setAuthor(su);
		msg2.setType(MessageType.PERSONAL);
		msg2.setTitle(title);
		msg2.setCategory(type);
		msg2.setContent(content);
		msg2.setLastUpdate(new Date());
		msg2 = msgRepo.save(msg2);
		for(SocialDistanceImpl distance:distances) {
			SocialUserImpl user = distance.getToUser();
			SocialMessageImpl msg = new SocialMessageImpl();
			msg.setUser(user);
			msg.setMessage(msg2);
			messageRepo.save(msg);
		}
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
			message.setId(msg.getId());
			SocialDistanceImpl distance = distanceRepo.findByFromUser_IdAndToUser_Id(userId, msg.getMessage().getAuthor().getId());
			if(distance!=null) {
				message.setDistance(distance.getDistance());
			}
		}
		return message;
	}

}
