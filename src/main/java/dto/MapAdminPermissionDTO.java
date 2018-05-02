package dto;

import java.io.Serializable;

public class MapAdminPermissionDTO implements Serializable{

	private static final long serialVersionUID = -731683936569857769L;
	
	private Integer mapAdminPermId;
	private Integer adminId;
	private Integer permissionId;
	private String username;
	private String permName;
	private String permDes;
	private String url;
	
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		this.url = url;
	}
	

}
