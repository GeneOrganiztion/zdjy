package controler.assess;

import com.abc.spring.FileUpload;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
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

import com.github.pagehelper.PageInfo;

import controler.base.BaseController;
import po.Assess;
import po.ResModel;
import service.AssessService;
import utils.ST;
import wepay.utils.HttpRequest;

@Controller
@RequestMapping(value = "/assess")
public class AssessController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AssessController.class);

    private String ASSESS_PAGE = "assess/assess";

    @Autowired
    private AssessService assessService;


    @RequestMapping(value = "/assessListPage")
    public ModelAndView assessPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(ASSESS_PAGE);
        return mv;
    }

    @RequestMapping(value = "/selectAssess")
    @ResponseBody
    public PageInfo<Assess> selectAssessByParams(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo<Assess> pageInfo = new PageInfo<Assess>();
        try {
            int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String proName = getParam("proName");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            if (!ST.isNull(beginTime)) {
                map.put("beginTime", beginTime + " 00:00:00");
            }
            if (!ST.isNull(endTime)) {
                map.put("endTime", endTime + " 59:59:59");
            }
            map.put("proName", proName);
            pageInfo = (PageInfo<Assess>) assessService.selectAssessByParams(map);
        } catch (Exception e) {
            logger.error("selectAdmin error:" + e);
        }
        return pageInfo;
    }

    @RequestMapping(value = "/saveAssess", method = RequestMethod.POST)
    @ResponseBody
    public ResModel saveAssess(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "proId") int proId,
        @RequestParam(value = "assessName") String assessName,
        @RequestParam(value = "assessContent") String assessContent) {
        ResModel resModel = new ResModel();
        Assess assess = new Assess();
        Integer returnId = null;
        try {
            assess.setProId(proId);
            assess.setUserName(assessName);
            assess.setAssessContent(assessContent);
            returnId = assessService.saveAssessReturnId(assess);
        } catch (Exception e) {
            logger.error("saveAssess error:" + e);
            resModel.setSuccess(false);
        }
        resModel.setReturnId(returnId);
        resModel.setSuccess(true);
        return resModel;
    }

    @RequestMapping(value = "/editAndSaveAssess", method = RequestMethod.POST)
    @ResponseBody
    public ResModel editAndSaveAssess(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "assessId") int assessId,
        @RequestParam(value = "proId") int proId,
        @RequestParam(value = "assessName") String assessName,
        @RequestParam(value = "assessContent") String assessContent) {
        ResModel resModel = new ResModel();
        Assess assess = new Assess();
        try {
            assess.setAssessId(assessId);
            assess.setProId(proId);
            assess.setUserName(assessName);
            assess.setAssessContent(assessContent);
            assessService.updateAssess(assess);
        } catch (Exception e) {
            logger.error("editAndSaveAssess error:" + e.toString());
            resModel.setSuccess(false);
        }
        resModel.setSuccess(true);
        return resModel;
    }

    @RequestMapping(value = "/saveAssessImg", method = RequestMethod.POST)
    @ResponseBody
    public ResModel saveAssessImg(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("inputFile") MultipartFile file) {
        ResModel resModel = new ResModel();
        String filepath = null;
        try {
            filepath = FileUpload.uploadFile(file, request);
        } catch (IOException e) {
            resModel.setSuccess(false);
            logger.error("saveAssessImg error:" + e);
        }
        resModel.setSuccess(true);
        resModel.setData(filepath);
        return resModel;
    }

    @RequestMapping(value = "/deleteAssess", method = RequestMethod.POST)
    @ResponseBody
    public ResModel deleteAssess(@RequestParam("assessIds") String assessIds) {
        ResModel resModel = new ResModel();
        if (ST.isNull(assessIds)) {
            resModel.setSuccess(false);
            return resModel;
        }
        try {
            assessService.deleteAssess(assessIds);
        } catch (Exception e) {
            logger.error("deleteAssess error:" + e);
        }
        resModel.setSuccess(true);
        return resModel;
    }

    @RequestMapping(value = "/selectAssessByAssessId", method = RequestMethod.POST)
    @ResponseBody
    public ResModel selectAssessByAssessId(@RequestParam("assessId") Integer assessId) {
        ResModel resModel = new ResModel();
        Assess assess = null;
        try {
            assess = assessService.selectAssessById(assessId);
        } catch (Exception e) {
            logger.error("selectAssessByAssessId error:" + e);
            resModel.setSuccess(false);
            return resModel;
        }
        resModel.setSuccess(true);
        resModel.setData(assess);
        return resModel;
    }


    /**
     * 手机端分页查评价
     */
    @RequestMapping(value = "/phoneSelectAssess")
    @ResponseBody
    public PageInfo<Assess> phoneSelectAssess(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        PageInfo<Assess> pageInfo = new PageInfo<Assess>();
        try {
            int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            Integer userId = null;
            Integer proId = null;
            if (!ST.isNull(getParam("userId"))) {
                userId = Integer.valueOf(getParam("userId"));// 用户ID
            }
            if (!ST.isNull(getParam("proId"))) {
                proId = Integer.valueOf(getParam("proId"));// 产品ID
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rowCount", oneRecord);
            map.put("pageNo", pageNo);
            map.put("userId", userId);
            map.put("proId", proId);
            pageInfo = (PageInfo<Assess>) assessService.selectAssessByParams(map);
        } catch (Exception e) {
            logger.error("phoneSelectAssess error:" + e);
        }
        return pageInfo;
    }
    @RequestMapping(value = "/phoneSaveAssess", method = RequestMethod.POST)
    @ResponseBody
    public ResModel phoneSaveAssess(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "proId") int proId,
        @RequestParam(value = "assessName") String assessName,
        @RequestParam(value = "assessContent") String assessContent) {
        ResModel resModel = new ResModel();
        Assess assess = new Assess();
        Integer returnId = null;
        try {
            assess.setProId(proId);
            assess.setUserName(assessName);
            assess.setAssessContent(assessContent);
            returnId = assessService.saveAssessReturnId(assess);
        } catch (Exception e) {
            logger.error("saveAssess error:" + e);
            resModel.setSuccess(false);
        }
        resModel.setReturnId(returnId);
        resModel.setSuccess(true);
        return resModel;
    }
}
