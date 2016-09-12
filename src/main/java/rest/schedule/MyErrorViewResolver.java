package rest.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import rest.controller.UserController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jtduan on 2016/9/12.
 */
@Controller
public class MyErrorViewResolver implements ErrorViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {
        logger.error("error");
        return new ModelAndView("error/5xx");
    }

}
