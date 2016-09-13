package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rest.constants.ResponseType;

/**
 * Created by jtduan on 2016/9/12.
 * 所有指定异常会进入该控制器，优先级高于ErrorViewResolver
 *
 */
@ControllerAdvice(basePackages = "rest.controller")
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String greetingExceptionHandler(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseType.FATAL.getMsg();
    }
}
