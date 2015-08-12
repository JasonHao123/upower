package jason.app.weixin.common.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY")
public class CategoryImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// ,generator="CATEGORY_ID_SEQ")
    // @SequenceGenerator(name="CATEGORY_ID_SEQ",sequenceName="CATEGORY_ID_SEQ",initialValue=1000)
    private Long id;
    
    @ManyToOne
    private CategoryImpl parent;

    
    @Column
    private String name;
    
    @Column
    private String type;
    

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column
    private String subType;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryImpl getParent() {
        return parent;
    }

    public void setParent(CategoryImpl parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    


}
