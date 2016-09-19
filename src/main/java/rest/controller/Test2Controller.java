package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.constants.DishType;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;
import rest.dao.DishRepo;
import rest.entity.Consumer;
import rest.entity.Dish;
import rest.entity.User;
import rest.module.websocket.Notification;
import rest.module.websocket.NotificationService;
import rest.service.BaseService;

/**
 * Created by jtduan on 2016/9/6.
 * 测试：判断hibernate session作用范围（生命期）
 * 测试结论：springboot 采用 openinView模式，hibernate session生命期在一个Request范围内
 */

@Deprecated
@Controller
@RequestMapping("/base2")
public class Test2Controller {

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BaseService baseService;

    private User u;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 结论1：懒加载可以延迟到view层
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String testsaveOrUpdate() {
        u = baseService.get();
        return "SUCCESS";
    }

    /**
     * 不同Request访问懒加载对象,报错！
     * @return
     */
    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    @ResponseBody
    public String get2() {
        System.out.println("===");
        if(u.getUserType() instanceof Consumer){
            System.out.println( ((Consumer) u.getUserType()).getRemain());
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseBody
    public String test2() throws InterruptedException {
        Dish d = new Dish();
        d.setPrice(8.9);
        d.setName("test");
        d.setDishType(DishType.SMALL);
        baseService.test2(d);
        return "SUCCESS";
    }
    @RequestMapping(value = "/test21", method = RequestMethod.GET)
    @ResponseBody
    public String test21() {
        Dish d = new Dish();
        d.setPrice(8.9);
        d.setName("test");
        d.setDishType(DishType.SMALL);
        baseService.test2_1(d);
        return "SUCCESS";
    }


    @RequestMapping(value = "/trans1", method = RequestMethod.GET)
    @ResponseBody
    public String trans1() throws InterruptedException {
        baseService.trans1();
        return "SUCCESS";
    }
    @RequestMapping(value = "/trans2", method = RequestMethod.GET)
    @ResponseBody
    public String trans2() throws InterruptedException {
        baseService.trans2();
        return "SUCCESS";
    }
}
