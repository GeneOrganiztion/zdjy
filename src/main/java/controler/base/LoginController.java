package controler.base;

import Captcha.CaptchaHelper;
import cache.PermissionCache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import po.Admin;
import service.AdminService;
import service.MapAdminPermissionService;
import utils.MD5Util;
import utils.ST;

/**
 * @author chenzhangsheng
 * @date 2016-12-7 18:31:52
 */

@Controller
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private AdminService adminService;

    @Autowired
    private MapAdminPermissionService mapAdminPermissionService;

    private final String LOGIN_JSP = "login";
    private final String INDEX_JSP = "index";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginJsp(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(LOGIN_JSP);
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(LOGIN_JSP);
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginMethod(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        Admin admin = null;
        String msg = null;
        String username = getParam("user");//用户名
        String password = getParam("psd");//密码
        String captcha = getParam("captcha");//
        if (ST.isNull(username) || ST.isNull(password)) {
            mv.addObject("msg", "用户名或密码不能为空");
            mv.setViewName(LOGIN_JSP);
            return mv;
        }
        if (!CaptchaHelper.validate(request, captcha)) {
            mv.addObject("msg", "验证码输入有误");
            mv.setViewName(LOGIN_JSP);
            return mv;
        }
        String passwordMd5 = MD5Util.getMD5(password);
        try {
            admin = (Admin) adminService.login(username, passwordMd5);
        } catch (Exception e) {
            logger.info("login.error=" + e);
            e.printStackTrace();
            msg = "服务器繁忙，请稍后重试";
            mv.setViewName(LOGIN_JSP);
            mv.addObject("msg", msg);
            return mv;
        }
        if (null == admin) {
            msg = "用户名或密码错误";
            mv.setViewName(LOGIN_JSP);
            mv.addObject("msg", msg);
            return mv;
        }
        if (admin.getIsdelete()) {
            msg = "当前用户已被禁用,请检查用户是否正常";
            mv.addObject("msg", msg);
            mv.setViewName(LOGIN_JSP);
            return mv;
        }
        session.setAttribute("SESSION_USER", admin);
        //初始化此用户的权限并放入缓存中
        try {
            //PermissionCache.destory();
            PermissionCache.init(mapAdminPermissionService, admin);
        } catch (Exception e) {
            logger.error("init PermissionCache error:" + e);
        }

        mv.setViewName("redirect:/redirect.do");

        return mv;
    }
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView logined(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(INDEX_JSP);
        return mv;
    }
}