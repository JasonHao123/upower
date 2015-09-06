package jason.app.weixin.social.entity;

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
@Table(name="MESSAGE_COMMENT")
public class MessageCommentImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @ManyToOne
	private SocialUserImpl author;
    
    @ManyToOne
    private MessageImpl message;
    
    @ManyToOne
    private MessageCommentImpl reference;
    
	public MessageCommentImpl getReference() {
		return reference;
	}

	public void setReference(MessageCommentImpl reference) {
		this.reference = reference;
	}

	@Column(columnDefinition="text")
    private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocialUserImpl getAuthor() {
		return author;
	}

	public void setAuthor(SocialUserImpl author) {
		this.author = author;
	}

	public MessageImpl getMessage() {
		return message;
	}

	public void setMessage(MessageImpl message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
    
}
