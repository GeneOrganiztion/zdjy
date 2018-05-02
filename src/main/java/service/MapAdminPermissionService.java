package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import dto.MapAdminPermissionDTO;
import po.MapAdminPermission;

public interface MapAdminPermissionService {
	
	public PageInfo<MapAdminPermissionDTO> selectAdminPermissionByParams(Map<String, Object> map);
	
	public boolean saveMapAdminPermission(MapAdminPermission mapAdminPermission);
	
	public List<MapAdminPermission> selectMapAdminPermission(MapAdminPermission mapAdminPermission);
	
	public boolean deleteMapAdminPermission(MapAdminPermission mapAdminPermission);
	
	public List<MapAdminPermission> viewAdminPermission(Integer adminId);
}
