package jason.app.weixin.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="REMEMBER_ME_TOKEN")
public class RememberMeTokenImpl {
    
    @ManyToOne
    private  UserImpl user;
    
    public UserImpl getUser() {
        return user;
    }
    public void setUser(UserImpl user) {
        this.user = user;
    }
    @Id
    private  String series;
    
    @Column
    private  String tokenValue;
    
    @Temporal(TemporalType.TIMESTAMP)
    private  Date date;
    

    public String getSeries() {
        return series;
    }
    public String getTokenValue() {
        return tokenValue;
    }
    public Date getDate() {
        return date;
    }

    public void setSeries(String series) {
        this.series = series;
    }
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
}
