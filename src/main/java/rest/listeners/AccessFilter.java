package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by jtduan on 2016/9/6.
 */

/**
 * 在Application.java中使用注解@ServletComponentScan后才可以使用
 *
 * @WebFilter @WebListener @WebServlet 注解
 */
//@WebFilter(filterName = "myFilter", urlPatterns = "/login")
@Deprecated
public class AccessFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void destroy() {
    }

    /**
     * 过滤器用于记录AccessHistory
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        System.out.println("过滤器过滤");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        User user = (User) httpRequest.getSession().getAttribute("user");
//        if(user!=null && user.getId()==0) user=null;
//        accessHistoryService.saveAccessHistory(user, httpRequest.getRemoteAddr(), httpRequest.getRequestURL().toString());
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
    }

}
