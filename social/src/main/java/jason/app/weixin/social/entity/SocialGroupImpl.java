package jason.app.weixin.social.entity;

import java.util.List;

public class SocialGroupImpl {
	private Long id;
	private String name;
	
	private List<MessageImpl> messages;
	private List<SocialUserImpl> members;
}
