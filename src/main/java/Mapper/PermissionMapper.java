package Mapper;

import java.util.List;
import java.util.Map;

import po.Permission;
import tk.mybatis.mapper.common.Mapper;

public interface PermissionMapper extends Mapper<Permission>{
	List<Permission> selectPermissionByParams(Map<String, Object> map);
}