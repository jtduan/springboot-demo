package rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rest.constants.CurrentUserUtils;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jtduan on 2016/9/13.
 */
@Controller
public class StaticController {
    @RequestMapping(value={"","/home","/index"})
    public String index(){
        return "home";
    }


    @RequestMapping(value="login",method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!CurrentUserUtils.INSTANCE.isAjaxRequest()) {
            return "login";
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ResponseType.PERMISSION_DENIED.getResponseStr());
        return null;
    }
}
