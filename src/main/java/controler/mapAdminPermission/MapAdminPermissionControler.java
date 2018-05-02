package controler.mapAdminPermission;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import controler.base.BaseController;
import dto.MapAdminPermissionDTO;
import po.MapAdminPermission;
import po.ResModel;
import service.MapAdminPermissionService;
import utils.ST;

@Controller
@RequestMapping(value="/mapAdminPermission")
public class MapAdminPermissionControler extends BaseController{
	
private static final Logger logger = LoggerFactory.getLogger(MapAdminPermissionControler.class);
	
	private String ADMINPERMISSION_PAGE = "adminPermission/adminPermission";
	
	@Autowired
	private MapAdminPermissionService mapAdminPermissionService;
	
	@RequestMapping(value="/mapAdminPermissionPage")
	public ModelAndView mapAdminPermissionPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ADMINPERMISSION_PAGE);
		return mv;
	}
	
	@RequestMapping(value = "/selectAdminPermission")
    @ResponseBody
    public PageInfo<MapAdminPermissionDTO> selectAdminPermission(HttpServletRequest request, HttpServletResponse response)throws Exception{
	 	String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo<MapAdminPermissionDTO> pageInfo = new PageInfo<MapAdminPermissionDTO>();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String username = getParam("username");
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
            map.put("username", username);
            map.put("permName", permName);
            pageInfo= (PageInfo<MapAdminPermissionDTO>)mapAdminPermissionService.selectAdminPermissionByParams(map);
		} catch (Exception e) {
			logger.error("selectAdminPermission error:" + e);
		}
        return pageInfo;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public ResModel delete(HttpServletRequest request,HttpServletResponse response){
		String mapAdminPermIdsTmp = getParam("mapAdminPermIds");
		ResModel resModel = new ResModel();
		boolean bl = false;
		try {
			String mapAdminPermIds = mapAdminPermIdsTmp.substring(0, mapAdminPermIdsTmp.length() - 1);
			String[] ids = mapAdminPermIds.split(",");
			MapAdminPermission mapAdminPermission = new MapAdminPermission();
			for(String id: ids){
				mapAdminPermission.setMapAdminPermId(Integer.valueOf(id));
				bl = mapAdminPermissionService.deleteMapAdminPermission(mapAdminPermission);
			}
		} catch (Exception e) {
			logger.error("delete error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}

}
