package jason.app.weixin.common.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Category {
    private Long id;
    private String name;
    @JsonIgnore
    private Category parent;
    private String type;
    private String subType;
    private boolean leaf;
    public Category(){}
    public Category(Long category) {
		// TODO Auto-generated constructor stub
    	this.id = category;
	}
	public boolean isLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    @JsonIgnore
    private List<Category> children;
    public List<Category> getChildren() {
        return children;
    }
    public void setChildren(List<Category> children) {
        this.children = children;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setParent(Category parent) {
        this.parent = parent;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Category getParent() {
        return parent;
    }
    public String getType() {
        return type;
    }
    public String getSubType() {
        return subType;
    }
}
