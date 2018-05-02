package controler.captcha;

import Captcha.CaptchaHelper;
import controler.base.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yakungao
 * @date 2017/12/5
 **/
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        httpResponse.setDateHeader("Expires", 0L);
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        httpResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setContentType("image/jpeg");
        try {
            CaptchaHelper.setInCache(httpRequest, httpResponse);
        }catch (Exception e){
            logger.info("getCaptcha error:" +e);
        }
    }
}
