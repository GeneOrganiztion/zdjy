package po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name ="home_page")
public class HomePage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 907664836939117567L;
	@Id
	private Integer homepageId;
	
    private String image;

    private String href;

    private Date createTime;

    private Date lastModifiedTime;

    private String comment;

    private String name;

    private Boolean isdelete;


    public Integer getHomepageId() {
        return homepageId;
    }

    public void setHomepageId(Integer homepageId) {
        this.homepageId = homepageId;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }
}