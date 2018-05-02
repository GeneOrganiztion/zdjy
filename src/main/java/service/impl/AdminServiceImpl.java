package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.AdminMapper;
import dto.AdminSetRoleDTO;
import po.Admin;
import po.MapAdminPermission;
import po.Permission;
import service.AdminService;
import service.MapAdminPermissionService;
import utils.MD5Util;
@Transactional
@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private MapAdminPermissionService mapAdminPermissionService;
	 
	public Admin login(String username,String password) throws Exception {
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		return adminMapper.selectOne(admin);
	}
	
	public PageInfo<Admin> selectAdminByParams(Map map) throws Exception {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Admin> list = adminMapper.selectAdminByParams(map);
	    PageInfo<Admin> page = new PageInfo<Admin>(list);
		return page;
	}
	
    public boolean deleteAdminByIds(List<Integer> ids) {
		try {
			adminMapper.deleteAdminByIds(ids);
			//删除admin同时删除 map_admin_role
			MapAdminPermission map = new MapAdminPermission();
			for(Integer id: ids){
				map.setAdminId(id);
				mapAdminPermissionService.deleteMapAdminPermission(map);
				//mar.setAdminId(id);
				//mapAdminRoleService.deleteMapAdminRole(mar);
			}
		} catch (Exception e) {
			logger.error("AdminServiceImpl deleteAdminByIds error:" + e);
			//手动回滚事务
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
    }
	public boolean saveAdmin(Admin admin){
		try {
			String passWordMD5 = MD5Util.getMD5(admin.getPassword());
			admin.setPassword(passWordMD5);
			adminMapper.insertSelective(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			return false;
		}
		return true;
	}
	public boolean updateAdmin(Admin admin){
		try {
			//String passWordMD5 = MD5Util.getMD5(admin.getPassword());
			//admin.setPassword(passWordMD5);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adminId", admin.getAdminId());
			List<Admin> adminList = adminMapper.selectAdminByParams(map);
			//如果前台传过来的密码没有更改，就不更改数据库中的密码
			if(adminList != null && adminList.size() > 0){
				Admin am = adminList.get(0);
				if(!am.getPassword().equals(admin.getPassword()) ){
					admin.setPassword(MD5Util.getMD5(admin.getPassword()));
				}
				admin.setLastModifiedTime(new Date());
				adminMapper.updateByPrimaryKeySelective(admin);
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			return false;
		}
		return true;
	}
	public Admin selectAdminByAdminId(Map<String,Object> map){
		List<Admin> adminList = new ArrayList<Admin>();
		try {
			adminList = adminMapper.selectAdminByParams(map);
		} catch (Exception e) {
			logger.error("selectAdminByAdminId error:" + e);
		}
		if(adminList != null && adminList.size() > 0){
			return adminList.get(0);
		}else{
			return new Admin();
		}
	}
	public List<Admin> validateAdmin(String name){
		Admin admin = new Admin();
		admin.setIsdelete(false);
		admin.setUsername(name);
		return adminMapper.select(admin);
	}
	public List<AdminSetRoleDTO> adminSetRole(Integer id){
		return adminMapper.adminSetRole(id);
	}

	/*public boolean saveAdminSetRole(Integer adminId, String roleIds) {
		if(ST.isNull(adminId) || ST.isNull(roleIds)){
			return false;
		}
		try {
			String[] strArr = roleIds.split(",");
			MapAdminRole mar = new MapAdminRole();
			mar.setAdminId(adminId);
			//设置角色之前清空以前的数据
			mapAdminRoleService.deleteMapAdminRole(mar);
			//保存数据
			for(String roleId: strArr){
				mar.setRoleId(Integer.valueOf(roleId));
				mapAdminRoleService.saveMapAdminRole(mar);
			}
		} catch (Exception e) {
			logger.error("saveAdminSetRole error:" + e);
			return false;
		}
		return true;
		
	}*/

	public boolean saveAdminSetPermission(String adminId, String permissionIds) {
		try {
			String[] ids =  permissionIds.split(",");
			MapAdminPermission mapAdminPermission = new MapAdminPermission();
			for(String id: ids){
				mapAdminPermission.setAdminId(Integer.valueOf(adminId));
				mapAdminPermission.setPermissionId(Integer.valueOf(id));
				List<MapAdminPermission> list = mapAdminPermissionService.selectMapAdminPermission(mapAdminPermission);
				if(list != null && list.size() > 0){
					continue;
				}
				mapAdminPermissionService.saveMapAdminPermission(mapAdminPermission);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("saveAdminSetPermission error:" + e);
			return false;
		}
		return true;
	}

	public List<Permission> viewAdminPermission(String adminId) {
		MapAdminPermission mapAdminPermission = new MapAdminPermission();
		mapAdminPermission.setAdminId(Integer.valueOf(adminId));
		
		// TODO Auto-generated method stub
		return null;
	}

}
