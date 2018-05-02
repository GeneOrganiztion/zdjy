package Mapper;

import java.util.List;
import java.util.Map;

import dto.MapAdminPermissionDTO;
import po.MapAdminPermission;
import tk.mybatis.mapper.common.Mapper;

public interface MapAdminPermissionMapper extends Mapper<MapAdminPermission>{
   /* int deleteByPrimaryKey(MapAdminPermissionKey key);

    int insert(MapAdminPermission record);

    int insertSelective(MapAdminPermission record);

    MapAdminPermission selectByPrimaryKey(MapAdminPermissionKey key);

    int updateByPrimaryKeySelective(MapAdminPermission record);

    int updateByPrimaryKey(MapAdminPermission record);*/
	
	List<MapAdminPermissionDTO> selectAdminPermissionByParams(Map<String, Object> map);
	
	List<MapAdminPermission> viewAdminPermission(Integer adminId);
}