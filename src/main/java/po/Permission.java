package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="permission")
public class Permission extends BaseEntity{
	
	private static final long serialVersionUID = 6855731735062358934L;

	@Id
	private Integer permissionId;
	
    private String permName;

    private String permDes;

    private String url;

    private Boolean isdelete;

    private Date lastModifiedTime;

    private Date createTime;
    
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermDes() {
		return permDes;
	}

	public void setPermDes(String permDes) {
		this.permDes = permDes;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}