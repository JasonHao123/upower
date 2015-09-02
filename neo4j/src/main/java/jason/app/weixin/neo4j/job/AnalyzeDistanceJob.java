package jason.app.weixin.neo4j.job;

import jason.app.weixin.neo4j.service.INeo4jService;
import jason.app.weixin.social.entity.SocialDistanceImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialDistance;
import jason.app.weixin.social.repository.SocialDistanceRepository;
import jason.app.weixin.social.repository.SocialUserRepository;
import jason.app.weixin.social.translator.SocialUserTranslator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnalyzeDistanceJob { // extends QuartzJobBean {
	private static Logger logger = LoggerFactory
			.getLogger(AnalyzeDistanceJob.class);

	@Autowired
	private SocialUserRepository userRepo;

	@Autowired
	private INeo4jService neo4jService;

	@Autowired
	private SocialDistanceRepository distanceRepo;

	private Calendar calendar = Calendar.getInstance();
	
	@Transactional
	@Scheduled(cron = "0/10 * *  * * ? ")
	// 每5秒执行一次
	public void myTest() {
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -5);
		
		Long su = userRepo.findUserToAnalyze(calendar.getTime());
		if(su==null)  {
			logger.info("no user need to sync distance");
			return;
		}else {
			logger.info("userId:"+su);
		}
		SocialUserImpl su2 = userRepo.findById(su);
//		SocialUser user = userRepo2.findByUserId(su);
		List<SocialDistance> distances = neo4jService.analyzeDistance(SocialUserTranslator.toDTO(su2));
		List<SocialDistanceImpl> result = new ArrayList<SocialDistanceImpl>();
		for(SocialDistance dis:distances) {
			SocialDistanceImpl impl = distanceRepo.findByFromUser_IdAndToUser_Id(su, dis.getTo().getId());
			if(impl==null) {
				impl = new SocialDistanceImpl();
				impl.setFromUser(su2);
				impl.setToUser(userRepo.findOne(dis.getTo().getId()));
			}
			impl.setDistance(dis.getDistance());
			impl.setRating(dis.getRating());
			result.add(impl);
		}
		distanceRepo.save(result);
		su2.setDistanceLastSync(new Date());
		userRepo.save(su2);
		/**
		String query = "start one=node("
				+ user.getId()
				+ ")  MATCH p = shortestPath(one-[:RELATE_TO*.."
				+ 7
				+ "]->(two:SocialUser))  RETURN distinct one.userId as from,two.userId as to,length(p) as distance";
		QueryResultBuilder users = (QueryResultBuilder) neo4jTemplate.query(
				query, null);
		Iterator<Map> items = users.as(Map.class).iterator();

		while (items.hasNext()) {
			Map item = (Map) items.next();
			final SocialRelationDTO dto = new SocialRelationDTO();

			try {
				BeanUtils.copyProperties(dto, item);
			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SocialDistanceImpl distance = distanceRepo
					.findByFromUser_IdAndToUser_Id(dto.getFrom(), dto.getTo());
			if (distance == null) {
				distance = new SocialDistanceImpl();
				distance.setFromUser(userRepo.findOne(dto.getFrom()));
				distance.setToUser(userRepo.findOne(dto.getTo()));
			}
			distance.setDistance(dto.getDistance());
			distanceRepo.save(distance);
		}
		*/
	}
}
