package controler.base;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import po.Admin;

/**
 * @author yakungao
 * @date 2017/12/15
 **/
public class SessionFiter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //需要跳过过滤的url
        String[] excludeUrl = {"captcha","login","phone","weixin","exit","assets","plugins","pagejs"};

        HttpSession httpSession = httpRequest.getSession();
        String url = httpRequest.getRequestURI();
        for(String excUrl: excludeUrl){
            if(url != null && url.contains(excUrl)){
                chain.doFilter(request, response);
                return;
            }
        }
        Admin user = (Admin) httpSession.getAttribute("SESSION_USER");
        if (user == null){
            //String url1 = new StringBuffer().append(httpRequest.getScheme()).append("://").append(httpRequest.getServerName()).append(":")
                //.append(httpRequest.getServerPort()).append("/login.do").toString();
            //httpResponse.sendRedirect(url1);
            httpRequest.getRequestDispatcher("/login.do").forward(httpRequest, response);
            return;
        }else if(user != null && "/".equals(url)){
            httpRequest.getSession().removeAttribute("SESSION_USER");
            httpRequest.getRequestDispatcher("/login.do").forward(httpRequest, response);
            return;
        }else{
            chain.doFilter(request, response);
            return;
        }

    }

    public void destroy() {

    }
}
