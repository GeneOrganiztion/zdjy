package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import dto.AdminSetRoleDTO;
import po.Admin;
import po.Permission;

public interface AdminService{
	
	public Admin login(String username,String password) throws Exception;
	
	public PageInfo<Admin> selectAdminByParams(Map<String, Object> map) throws Exception;
	
	//public void deleteAdminByIds(List<Integer> ids);

	public boolean deleteAdminByIds(List<Integer> ids);
	
	public boolean saveAdmin(Admin admin);
	
	public boolean updateAdmin(Admin admin);
	
	public Admin selectAdminByAdminId(Map<String,Object> map);

	public List<Admin> validateAdmin(String name);
	
	public List<AdminSetRoleDTO> adminSetRole(Integer id);
	
	//public boolean saveAdminSetRole (Integer adminId, String roleIds);
	
	public boolean saveAdminSetPermission(String adminId, String permissionIds);
	
	public List<Permission> viewAdminPermission(String adminId);
}
