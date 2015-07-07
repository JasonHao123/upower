package jason.app.weixin.social.entity;

import java.util.List;

public class SocialGroup {
	private Long id;
	private String name;
	
	private List<Message> messages;
	private List<SocialUserImpl> members;
}
