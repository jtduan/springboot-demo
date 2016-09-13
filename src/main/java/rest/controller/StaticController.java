package rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jtduan on 2016/9/13.
 */
@Controller
public class StaticController {
    @RequestMapping(value={"","/home","/index"})
    public String index(){
        return "home";
    }
}
