package service;

import java.util.Map;

import po.Permission;

import com.github.pagehelper.PageInfo;


public interface PermissionService {
	
	public PageInfo<Permission> selectPermissionByParams(Map<String, Object> map) throws Exception;	
	
	public boolean savePermission(Permission permission);
	
	public Permission selectPermissionByPermissionId(Integer id);
	
	public boolean updatePermission(Permission permission);
	
	public boolean deleteByPermissionIds(String ids);
	
}
