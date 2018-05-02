package cache;

import java.util.ArrayList;
import java.util.List;

import po.Admin;
import po.MapAdminPermission;
import service.MapAdminPermissionService;

public class PermissionCache {
	
	private static List<MapAdminPermission> permList = new ArrayList<MapAdminPermission>();
	
	//@Autowired
	//private static PermissionService permissionService;
	
	public static void init(MapAdminPermissionService mapAdminPermissionService, Admin admin){
		//permList = permissionService.selectPermByAdminId(admin.getAdminId());
		permList = mapAdminPermissionService.viewAdminPermission(admin.getAdminId());
	}
	
	public static List<MapAdminPermission> getPermList(){
		return permList;
	} 
	
	public static void destory(){
		permList = null;
	}

}
