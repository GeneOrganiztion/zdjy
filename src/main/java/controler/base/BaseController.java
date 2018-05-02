package controler.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import po.Admin;
import utils.ST;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
   
    
    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected String getParam(String k) {
    	return getParam(k, null);  
    }

    protected String getParam(String k, String def) {
        return StringUtils.trim(ST.getDefault(request.getParameter(k), def));
    }
    
    public HttpSession getNowSession()
    {
      ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
      return attrs.getRequest().getSession();
    }
    
    public Admin getNowUser()
    {
      Object object = getNowSession().getAttribute("SESSION_USER");
      if (object != null) {
    	  Admin adminInfo = (Admin)object;
        return adminInfo;
      }
      return null;
    }
    
    public Integer getUserId()
    {
    	Admin adminInfo = getNowUser();
    	if(!ST.isNull(adminInfo)){
    		return adminInfo.getAdminId();
    	}
    	return null;
    }
    public String getPostJSON(HttpServletRequest request) {
        String result="";
        try {
            result=inputStreamToString(request.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    public String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n,"utf-8"));
        }
        return out.toString();
    }
    
}
