package rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rest.constants.ResponseType;
import rest.schedule.MyFilter;
import rest.schedule.MyHttpSessionListener;
import rest.schedule.MyInterceptor;
import rest.schedule.MyServletContextListener;

import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtduan on 2016/9/6.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    /**
     * 不使用注解方式时，可以使用此方式注册过滤器和监听器
     * 这个Bean可以被放到任何类中而不仅限于此类
     * @return
     */
/*    @Bean
    public FilterRegistrationBean registrationFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("greeting");
        registrationBean.setFilter(new MyFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean registrationListenerBean() {

        ServletListenerRegistrationBean  registrationBean = new ServletListenerRegistrationBean ();
        registrationBean.setName("MyServletContextListener");
        registrationBean.setListener(new MyServletContextListener());
        registrationBean.setOrder(2);
        return registrationBean;
    }
    @Bean
    public ServletListenerRegistrationBean registrationSessionListenerBean() {
        ServletListenerRegistrationBean  registrationBean = new ServletListenerRegistrationBean ();
        registrationBean.setName("MyHttpSessionListener");
        registrationBean.setListener(new MyHttpSessionListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }*/

    /**
     * 配制简单的映射
     * 注意：此处映射不会被aop.AccessLog捕获！
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ResponseTypeMessageConverter converter = new ResponseTypeMessageConverter();
        converters.add(converter);
        super.extendMessageConverters(converters);
    }

}
