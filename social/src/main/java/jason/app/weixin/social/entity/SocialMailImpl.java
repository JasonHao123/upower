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
@Table(name="MAIL")
public class SocialMailImpl {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    
	    @ManyToOne
		private SocialUserImpl from;
		
	    @ManyToOne
		private SocialUserImpl to;
		
	    @Column(length=1024)
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

		public SocialUserImpl getFrom() {
			return from;
		}

		public void setFrom(SocialUserImpl from) {
			this.from = from;
		}

		public SocialUserImpl getTo() {
			return to;
		}

		public void setTo(SocialUserImpl to) {
			this.to = to;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
}
