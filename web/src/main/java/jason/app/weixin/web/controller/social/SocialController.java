package jason.app.weixin.web.controller.social;

import jason.app.weixin.security.model.User;
import jason.app.weixin.security.service.ISecurityService;
import jason.app.weixin.social.entity.AddFriendLinkImpl;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.repository.AddFriendLinkRepository;
import jason.app.weixin.social.repository.SocialUserRepository;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/social")
public class SocialController {

	@Autowired
	private ISecurityService service;

	@Autowired
	private SocialUserRepository socailUserRepo;

	@Autowired
	private AddFriendLinkRepository linkRepo;

	private static final Logger logger = LoggerFactory
			.getLogger(SocialController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/addfriend", method = RequestMethod.GET)
	@Transactional
	public String home(Model model,
			@RequestParam(required = false, value = "id") String id) {
		logger.info("Welcome home!");
		User user = service.getCurrentUser();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		if (StringUtils.isEmpty(id)) {
			SocialUserImpl userImpl = socailUserRepo.findOne(user.getId());
			AddFriendLinkImpl link = linkRepo
					.findByUser_IdAndCreateDateGreaterThan(user.getId(),
							calendar.getTime());
			if (link == null) {
				link = new AddFriendLinkImpl();
				link.setUser(userImpl);
				link.setCreateDate(new Date());
				link = linkRepo.save(link);
			}
			return "redirect:/social/addfriend.do?id=" + link.getId();
		} else {
			AddFriendLinkImpl link = linkRepo.findOne(id);

			boolean expired = calendar.getTime()
					.compareTo(link.getCreateDate()) >= 0;
			model.addAttribute("link", link);
			model.addAttribute("expired", expired);
			model.addAttribute("self",
					link.getUser().getId().equals(user.getId()));

		}

		return "social.addfriend";
	}
}
