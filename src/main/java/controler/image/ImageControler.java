package controler.image;

import java.util.List;
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
import po.Image;
import po.ResModel;
import service.ImageService;
import utils.ST;

/**
 * @author yakungao
 * @date 2018/1/3
 **/
@Controller
@RequestMapping(value = "/image")
public class ImageControler {

    private static final Logger logger = LoggerFactory.getLogger(ImageControler.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    @ResponseBody
    public ResModel saveImage(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("paths") String paths, @RequestParam("assessId") int assessId) {
        ResModel resModel = new ResModel();
        if (ST.isNull(paths) || ST.isNull(assessId)) {
            resModel.setSuccess(false);
            return resModel;
        }
        String[] pathArr = null;
        List<Image> list = null;
        try {
            pathArr = paths.split(";");
            list = imageService.saveImages(pathArr, assessId);

        } catch (Exception e) {
            logger.error("saveImage error:" + e);
            resModel.setSuccess(false);
            return resModel;
        }
        resModel.setSuccess(true);
        resModel.setData(list);
        return resModel;
    }

    @RequestMapping(value = "/delImage", method = RequestMethod.POST)
    @ResponseBody
    public ResModel delImage(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("imgId") int imgId) {
        ResModel resModel = new ResModel();
        try {
            imageService.delImageById(imgId);
        } catch (Exception e) {
            logger.error("delImage error:" + e);
            resModel.setSuccess(false);
            return resModel;
        }
        resModel.setSuccess(true);
        return resModel;
    }

    @RequestMapping(value = "/findImgByAssessId", method = RequestMethod.POST)
    @ResponseBody
    public ResModel findImgByAssessId(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("assessId") Integer assessId) {
        ResModel resModel = new ResModel();
        List<Image> imageList = null;
        try {
            imageList = imageService.findImageByAssessId(assessId);
        } catch (Exception e) {
            logger.error("findImgByAssessId error:" + e);
            resModel.setSuccess(false);
            return resModel;
        }
        resModel.setData(imageList);
        resModel.setSuccess(true);
        return resModel;
    }

}
