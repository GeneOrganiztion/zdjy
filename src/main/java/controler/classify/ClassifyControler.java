package controler.classify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import po.Classify;
import po.ResModel;
import service.ClassifyService;
import utils.ST;

import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;

import controler.base.BaseController;

@Controller
@RequestMapping(value = "/classify")
public class ClassifyControler extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ClassifyControler.class);

	private String CLASSIFY_PAGE = "classify/classifyList";

	@Autowired
	private ClassifyService classifyService;

	@RequestMapping(value = "/classifyListPage")
	@ResponseBody
	public ModelAndView classifyListPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(CLASSIFY_PAGE);
		return mv;
	}

	@RequestMapping(value = "/seletcClassify")
	@ResponseBody
	public PageInfo<Classify> seletcClassify(HttpServletRequest request, HttpServletResponse response) {
		String sidx = getParam("sidx");// 排序字段;
		String sord = getParam("sord");// 升序降序;
		PageInfo<Classify> pageInfo = new PageInfo<Classify>();
		try {
			int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
			int pageNo = Integer.valueOf(getParam("page"));// 第几页
			String claName = getParam("claName");
			String beginTime = getParam("beginTime");
			String endTime = getParam("endTime");
			String classifyId = getParam("classifyId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sidx", sidx);// 排序字段
			map.put("sord", sord);// 升序降序
			map.put("rowCount", oneRecord);// 一页几行
			map.put("pageNo", pageNo);
			map.put("claName", claName);
			map.put("classifyId", classifyId);
			if (!ST.isNull(beginTime)) {
				map.put("beginTime", beginTime + " 00:00:00");
			}
			if (!ST.isNull(endTime)) {
				map.put("endTime", endTime + " 59:59:59");
			}
			pageInfo = (PageInfo<Classify>) classifyService.selectClassifyParams(map);
		} catch (Exception e) {
			logger.error("seletcClassify error:" + e);
		}
		return pageInfo;
	}

	@RequestMapping(value = "/selectAllClassify")
	@ResponseBody
	public List<Classify> selectAllClassify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Classify> list = new ArrayList<Classify>();
		try {
			list = classifyService.selectAllClassify();
		} catch (Exception e) {
			logger.error("selectAllClassify error:" + e);
		}
		return list;
	}

	@RequestMapping(value = "/saveOneClassify", method = RequestMethod.POST)
	@ResponseBody
	public ResModel saveOneClassify(HttpServletRequest request, @RequestParam("file") MultipartFile file)
			throws Exception {
		ResModel resModel = new ResModel();
		String claName1 = getParam("claName");
		String claName = new String(claName1.getBytes("ISO-8859-1"), "UTF-8");
		try {
			Classify cls = new Classify();
			String filepath = FileUpload.uploadFile(file, request);
			cls.setClaName(claName);
			cls.setClaContent(filepath);
			cls.setIsdelete(false);
			cls.setCreateTime(new Date());
			cls.setLastModifiedTime(new Date());
			Integer id = classifyService.insertOneClassifyReturnId(cls);
			resModel.setReturnId(id);

		} catch (Exception e) {
			logger.error("saveOneClassify error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}

	@RequestMapping(value = "/removeOneClassfyById")
	@ResponseBody
	public ResModel removeReportById(HttpServletRequest request, HttpServletResponse response) {
		ResModel resModel = new ResModel();
		String oneClassifyId = getParam("oneClassifyId");
		if (ST.isNull(oneClassifyId)) {
			resModel.setSuccess(false);
			return resModel;
		}
		Classify cls = new Classify();
		cls.setClassifyId(Integer.valueOf(oneClassifyId));
		boolean bl = false;
		try {
			// 删除服务器上的图片
			Classify clsfy = classifyService.selectOneClassify(cls);
			FileUpload.deleteObject(clsfy.getClaContent());
			bl = classifyService.delOneClassifyById(cls);
		} catch (Exception e) {
			logger.error("removeOrderById error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		if (!bl) {
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}

	@RequestMapping(value = "/deleteOneClassify") // 删除一级分类
	@ResponseBody
	public ResModel deleteOneClassify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String ids = getParam("oneClassifyIds");
		Classify cls = new Classify();
		try {
			List<Integer> list = ST.StringToList(ids);
			for (Integer id : list) {
				cls.setClassifyId(id);
				// 删除服务器上的图片
				Classify clsfy = classifyService.selectOneClassify(cls);
				FileUpload.deleteObject(clsfy.getClaContent());
				classifyService.delClassify(cls);
			}
		} catch (Exception e) {
			logger.error("deleteOneClassify error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}

	@RequestMapping(value = "/selectOneClassify")
	@ResponseBody
	public Classify selectOneClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String classifyId = getParam("classifyId");
		Classify cls = new Classify();
		if(ST.isNull(classifyId)){
			return cls;
		}
		cls.setClassifyId(Integer.valueOf(classifyId));
		return classifyService.selectOneClassify(cls);
	}  
	
	@RequestMapping(value = "/delectOneClassifyPic")
	@ResponseBody
	public ResModel delectOneClassifyPic(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String oneClassifyId = getParam("oneClassifyId");
		Classify cls = new Classify();
		if(ST.isNull(oneClassifyId)){
			return resModel;
		}
		cls.setClassifyId(Integer.valueOf(oneClassifyId));
		cls.setClaContent("");
		cls.setIsdelete(false);
		cls.setLastModifiedTime(new Date());
		try {
			classifyService.updateClassify(cls);
		} catch (Exception e) {
			logger.error("delectOneClassifyPic error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	} 
	
	@RequestMapping(value = "/editOneClassify")
	@ResponseBody
	public ResModel editOneClassify(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String claName1 = getParam("claName");
		 String claName =  new String(claName1.getBytes("ISO-8859-1"),"UTF-8");
		 String classifyId = getParam("classifyId");
		 
		 try {
			 Classify cls = new Classify();
			 String filepath = FileUpload.uploadFile(file, request);
			 cls.setClassifyId(Integer.valueOf(classifyId));
			 cls.setClaName(claName);
			 cls.setClaContent(filepath);
			 cls.setLastModifiedTime(new Date());
			 
			 classifyService.updateClassify(cls);
			 
		} catch (Exception e) {
			logger.error("editOneClassify error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/phoneSelectAllClassify")
	@ResponseBody
	public List<Classify> phoneSelectCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Classify> list = new ArrayList<Classify>();
		try {
			list = classifyService.selectAllClassify();
		} catch (Exception e) {
			logger.error("phoneSelectCart error:" + e);
		}
		return list;
	}
}
