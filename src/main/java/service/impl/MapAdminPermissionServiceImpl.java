package service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import po.MapAdminPermission;
import service.MapAdminPermissionService;
import Mapper.MapAdminPermissionMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import dto.MapAdminPermissionDTO;

@Transactional
@Service
public class MapAdminPermissionServiceImpl implements MapAdminPermissionService {
	
	private static final Logger logger = LoggerFactory.getLogger(MapAdminPermissionServiceImpl.class);

	@Autowired
	private MapAdminPermissionMapper mapAdminPermissionMapper;
	
	public PageInfo<MapAdminPermissionDTO> selectAdminPermissionByParams(Map<String, Object> map) {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<MapAdminPermissionDTO> list = mapAdminPermissionMapper.selectAdminPermissionByParams(map);
	    PageInfo<MapAdminPermissionDTO> page = new PageInfo<MapAdminPermissionDTO>(list);
		return page;
	}

	
	public boolean saveMapAdminPermission(MapAdminPermission mapAdminPermission) {
		// TODO Auto-generated method stub
		try {
			mapAdminPermissionMapper.insertSelective(mapAdminPermission);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("saveMapAdminPermission erro:" + e);
			return false;
		}
		return true;
	}

	public List<MapAdminPermission> selectMapAdminPermission(MapAdminPermission mapAdminPermission) {
		// TODO Auto-generated method stub
		mapAdminPermission.setIsdelete(false);
		return mapAdminPermissionMapper.select(mapAdminPermission);
	}

	public boolean deleteMapAdminPermission(MapAdminPermission mapAdminPermission) {
		// TODO Auto-generated method stub
		try {
			mapAdminPermissionMapper.delete(mapAdminPermission);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("deleteMapAdminPermission error:" + e);
			return false;
		}
		return true;
	}

	public List<MapAdminPermission> viewAdminPermission(Integer adminId) {
		// TODO Auto-generated method stub
		return mapAdminPermissionMapper.viewAdminPermission(adminId);
	}


}
