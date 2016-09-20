package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jtduan on 2016/9/12.
 * 所有没有mapping映射的页面和所有发生异常的页面均会进入此控制器
 * 优先级低于ExceptionController
 */
@Controller
public class MyErrorViewResolver implements ErrorViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {
        logger.error(request.getRequestURI());

        return new ModelAndView("error/5xx");
    }
}
