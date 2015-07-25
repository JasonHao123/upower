package jason.app.weixin.social.entity;

import jason.app.weixin.social.constant.MessageType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="MESSAGE")
public class MessageImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @ManyToOne
    private SocialUserImpl author;
    
    @Column
    private Long category;
    
    @Column
    private String title;
	
	public SocialUserImpl getAuthor() {
		return author;
	}

	public void setAuthor(SocialUserImpl author) {
		this.author = author;
	}

	@ManyToOne
	private MessageImpl originalMessage;
	
	// personal message or group message
	@Column
	private MessageType type;
	
	// synchronize with neo4j
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(length=2048)
	private String content;

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageImpl getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(MessageImpl originalMessage) {
		this.originalMessage = originalMessage;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
