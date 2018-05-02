package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
@Table(name ="classify")
public class Classify extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8454632770254099727L;

	@Id
	private Integer classifyId;
	
    private String claName;

    private String claContent;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }
    
    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName == null ? null : claName.trim();
    }

    public String getClaContent() {
        return claContent;
    }

    public void setClaContent(String claContent) {
        this.claContent = claContent == null ? null : claContent.trim();
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
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
}