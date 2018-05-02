package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name ="map_admin_permission")
public class MapAdminPermission extends BaseEntity{
	
	private static final long serialVersionUID = -2519699610968036301L;

	@Id
	private Integer mapAdminPermId;

    private Integer adminId;

    private Integer permissionId;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    
    @Transient
    private Admin admin;
    
    @Transient
    private Permission permission;

    public Integer getMapAdminPermId() {
        return mapAdminPermId;
    }

    public void setMapAdminPermId(Integer mapAdminPermId) {
        this.mapAdminPermId = mapAdminPermId;
    }
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
    
}