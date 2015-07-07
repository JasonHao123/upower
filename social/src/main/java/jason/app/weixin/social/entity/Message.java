package jason.app.weixin.social.entity;

import java.util.Date;

public class Message {

	private Long id;
	
	private Long originalId;
	
	// personal message or group message
	private Integer type;
	
	// synchronize with neo4j
	private Date lastSync;
}
