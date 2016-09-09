package rest.schedule;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by jtduan on 2016/9/6.
 */

/**
 * 在Application.java中使用注解@ServletComponentScan后才可以使用
 * @WebFilter @WebListener @WebServlet 注解
 */
//@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("执行过滤操作");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
    }

}
