package dto;

import java.io.Serializable;

public class AdminSetRoleDTO implements Serializable {

    private static final long serialVersionUID = -685880548423581225L;

    private Integer roleId;
    private String roleDisplayName;
    private Integer id;
    
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleDisplayName() {
		return roleDisplayName;
	}
	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
    
}
