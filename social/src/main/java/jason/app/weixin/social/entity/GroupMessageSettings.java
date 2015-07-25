package jason.app.weixin.social.entity;

public class GroupMessageSettings {
	// PK user & group
	private SocialUserImpl user;
	private SocialGroupImpl group;
	
	private Boolean findFriend;
	private Boolean supplyAndDemand;
	private Boolean secondHand;
	private Boolean postJob;
	private Boolean others;
	private Integer distance;
}
