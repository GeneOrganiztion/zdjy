package controler.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.MailUtil;
import org.apache.commons.collections.map.HashedMap;
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
import dto.AdminSetRoleDTO;
import po.Admin;
import po.MapAdminPermission;
import po.ResModel;
import service.AdminService;
import service.MapAdminPermissionService;
import utils.ST;

@Controller
@RequestMapping(value="/admin")
public class AdminControler extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AdminControler.class);
	
	private String ADMIN_PAGE = "admin/admin";
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private MailUtil mailUtilTool;
	
	@Autowired
	private MapAdminPermissionService mapAdminPermissionService;
	
	@RequestMapping(value="/adminPage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ADMIN_PAGE);
		return mv;
	}
	@RequestMapping(value = "/selectAdmin")
    @ResponseBody
    public PageInfo<Admin> selectAdminByParams(HttpServletRequest request, HttpServletResponse response)throws Exception{


		//测试发邮件
		/*Map<String, Object> model = new HashedMap();
		model.put("operate", "增加");
		model.put("domainName","test");
		model.put("ruleNum", "test");
		model.put("ruleName", "ip黑名单");
		model.put("userName", "test");
		model.put("forbiddenDOs", null);
		//定义邮件主题
		String subject = "七牛CDN手动配置";
		//定义邮件模板名
		String template = "qiNiuCdnOwn.ftl";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		model.put("nowDate", sdf.format(new Date()));
		String servername ="";
		Integer serverport = 12;
		String username = "";
		String password = "";
		String from ="";
		String nickname = "test";
		String[] receives = {""};
		mailUtilTool.sendMailToReceivesByTemplate(servername, serverport, username, password, from, receives, null, subject, model, template,nickname);*/



	 	String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo<Admin> pageInfo = new PageInfo<Admin>();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String userName = getParam("userName");
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
            map.put("userName", userName);
            pageInfo= (PageInfo<Admin>)adminService.selectAdminByParams(map);
		} catch (Exception e) {
			logger.error("selectAdmin error:" + e);
		}
        return pageInfo;
	}
	@RequestMapping(value="/delete")
	@ResponseBody
	public boolean delete(HttpServletRequest request,HttpServletResponse response){
		String adminIds = getParam("adminIds");
		try {
			List<Integer> list = ST.StringToList(adminIds);
			adminService.deleteAdminByIds(list);
		} catch (Exception e) {
			logger.error("delete error:" + e);
			return false;
		}
		return true;
	}
	@RequestMapping(value="/saveAdmin")
	@ResponseBody
	public ResModel saveAdmin(@ModelAttribute Admin admin,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = adminService.saveAdmin(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		resModel.setMsg("插入成功！");
		return resModel;
	}
	@RequestMapping(value="/selectAdminByAdminId")
	@ResponseBody
	public Admin selectAdminByAdminId(HttpServletRequest request,HttpServletResponse response){
		String adminId = getParam("adminId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminId", adminId);
		Admin admin = new Admin();
		try {
			admin = adminService.selectAdminByAdminId(map);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
		}
		return admin;
	}
	@RequestMapping(value="/updateAdmin")
	@ResponseBody
	public ResModel updateAdmin(@ModelAttribute Admin admin,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = adminService.updateAdmin(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}
	/**
	 * 验证是否存在此用户名
	 * @param request
	 * @param response    resModel    true:存在重复的用户名      false:不存在重复的用户名
	 * @return
	 */
	@RequestMapping(value="/validateAdmin")
	@ResponseBody
	public ResModel validateAdmin(HttpServletRequest request,HttpServletResponse response){
		String name = getParam("name");
		ResModel  resModel = new ResModel();
		boolean bl = false;
		List<Admin> listAdmin = null;
		try {
			listAdmin = adminService.validateAdmin(name);
		} catch (Exception e) {
			logger.error("validateAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		if(listAdmin != null && listAdmin.size() > 0){
			resModel.setSuccess(true);
		}else{
			resModel.setSuccess(bl);
		}
		
		return resModel;
	}
	
	@RequestMapping(value = "/adminSetRole")
	@ResponseBody
    public List<AdminSetRoleDTO> adminSetRole(HttpServletRequest request,HttpServletResponse response) {
		List<AdminSetRoleDTO> list = new ArrayList<AdminSetRoleDTO>();
		String adminId = getParam("adminId");
		if(ST.isNull(adminId)){
			return list;
		}
        list = adminService.adminSetRole(Integer.valueOf(adminId));
        return list;
    }
	
	/*@RequestMapping(value = "/saveAdminSetRole")
	@ResponseBody
    public ResModel saveAdminSetRole(HttpServletRequest request,HttpServletResponse response) {
		ResModel resModel = new ResModel();
		String adminId = getParam("adminId");
		String roleIds = getParam("roleIds");
		if(ST.isNull(adminId) || ST.isNull(roleIds)){
			resModel.setSuccess(false);
			return resModel;
		}
		boolean bl = adminService.saveAdminSetRole(Integer.valueOf(adminId), roleIds);
		resModel.setSuccess(bl);
        return resModel;
    }*/
	
	@RequestMapping(value="/saveAdminSetPermission")
	@ResponseBody
	public ResModel saveAdminSetPermission(HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		String adminId = getParam("adminId");
		String permissionIdTmps = getParam("permissionIds");
		if(ST.isNull(adminId) || ST.isNull(permissionIdTmps)){
			resModel.setSuccess(false);
			return resModel;
		}
		String permissionIds = permissionIdTmps.substring(0, permissionIdTmps.length() - 1);
		boolean bl = false;
		try {
			bl = adminService.saveAdminSetPermission(adminId, permissionIds);
		} catch (Exception e) {
			logger.error("saveAdminSetPermission error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}
	@RequestMapping(value = "/viewAdminPermission")
	@ResponseBody
    public List<MapAdminPermission> viewAdminPermission(HttpServletRequest request,HttpServletResponse response) {
		List<MapAdminPermission> list = new ArrayList<MapAdminPermission>();
		String adminId = getParam("adminId");
		if(ST.isNull(adminId)){
			return list;
		}
		list = mapAdminPermissionService.viewAdminPermission(Integer.valueOf(adminId));
		
        return list;
    }
	
}
