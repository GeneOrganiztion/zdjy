package controler.permission;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import controler.base.BaseController;
import po.Permission;
import po.ResModel;
import service.PermissionService;
import utils.ST;

@Controller
@RequestMapping(value="/permission")
public class PermissionControler extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(PermissionControler.class);
	
	private String PERMINSSION_PAGE = "permission/permission";
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value="/permissionPage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PERMINSSION_PAGE);
		return mv;
	}
	@RequestMapping(value = "/selectPermission")
    @ResponseBody
    public PageInfo<Permission> selectPermissionByParams(HttpServletRequest request, HttpServletResponse response)throws Exception{
	 	String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo<Permission> pageInfo = new PageInfo<Permission>();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String permName = getParam("permName");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            map.put("permName", permName);
            pageInfo= (PageInfo<Permission>)permissionService.selectPermissionByParams(map);
		} catch (Exception e) {
			logger.error("selectPermissionByParams error:" + e);
		}
        return pageInfo;
	}
	@RequestMapping(value="/savePermission")
	@ResponseBody
	public ResModel savePermission(@ModelAttribute Permission permission,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = permissionService.savePermission(permission);
		} catch (Exception e) {
			logger.error("savePermission error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		resModel.setMsg("插入成功！");
		return resModel;
	}
	@RequestMapping(value="/selectPermissionByPermissionId")
	@ResponseBody
	public Permission selectPermissionByPermissionId(HttpServletRequest request,HttpServletResponse response){
		Permission permission = new Permission();
		String permissionId = getParam("permissionId");
		if(ST.isNull(permissionId)){
			return permission;
		}
		try {
			permission = permissionService.selectPermissionByPermissionId(Integer.valueOf(permissionId));
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
		}
		return permission;
	}
	@RequestMapping(value="/updatePermission")
	@ResponseBody
	public ResModel updatePermission(@ModelAttribute Permission permission,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = permissionService.updatePermission(permission);
		} catch (Exception e) {
			logger.error("updatePermission error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}
	@RequestMapping(value="/delete")
	@ResponseBody
	public ResModel delete(HttpServletRequest request,HttpServletResponse response){
		String permissionIds = getParam("permissionIds");
		ResModel resModel = new ResModel();
		boolean bl = false;
		try {
			bl = permissionService.deleteByPermissionIds(permissionIds);
		} catch (Exception e) {
			logger.error("delete error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}
}
