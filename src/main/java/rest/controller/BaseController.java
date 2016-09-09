package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.backthreads.CustomQueue;
import rest.constants.DishType;
import rest.dao.DishRepo;
import rest.entity.Dish;

/**
 * Created by jtduan on 2016/9/6.
 */

@Controller
public class BaseController {

    @Autowired
    private DishRepo dishRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = {"","index"})
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("test1")
    public String test(){
        Dish dish = dishRepo.findByNameAndType("红烧排骨", DishType.STANDARD);
        CustomQueue.push(dish,3);
        return "success";
    }

    @ResponseBody
    @RequestMapping("test2")
    public String test2(){
        Dish dish = dishRepo.findByNameAndType("红烧排骨", DishType.SMALL);
        CustomQueue.push(dish,1);
        return "success";
    }

}
