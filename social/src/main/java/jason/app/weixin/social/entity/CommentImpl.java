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
@Table(name="COMMENT")
public class CommentImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @ManyToOne
	private SocialUserImpl author;
    
    @ManyToOne
	private SocialUserImpl target;

    public SocialUserImpl getTarget() {
		return target;
	}

	public void setTarget(SocialUserImpl target) {
		this.target = target;
	}

	@Column
    private Float rating;
	
    @Column
	private String message;
    
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
