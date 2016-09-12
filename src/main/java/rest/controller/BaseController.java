package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.backthreads.CustomQueue;
import rest.constants.DishType;
import rest.constants.MyErrorType;
import rest.constants.ResponseType;
import rest.constants.VIP;
import rest.dao.DishRepo;
import rest.entity.Dish;
import rest.entity.User;

import javax.annotation.security.PermitAll;

/**
 * Created by jtduan on 2016/9/6.
 */

@Deprecated
@Controller
@RequestMapping("/base")
public class BaseController {

    @Autowired
    private DishRepo dishRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("message_converter")
//    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String message_convert(){
        return "base/message_converter";
    }

    @RequestMapping(value = "convertEnumToStr", produces = { "text/x-responseType" })
    @ResponseBody
    @PermitAll
    public ResponseType convertEnumToStr() {
        return ResponseType.SUCCESS;
    }

    /**
     * 此处为@RequestBody
     * @param type
     * @return
     */
    @RequestMapping(value = "convertStrToEnum", produces = { "text/x-responseType" })
    @ResponseBody
    @PermitAll
    public ResponseType convertStrToEnum(@RequestBody ResponseType type) {
        logger.info("测试-Type类型："+type.getCode()+",Msg:"+type.getMsg());
        return ResponseType.SUCCESS;
    }

    @RequestMapping("exception")
    @ResponseBody
    public Dish exception(){
//        return new Dish("name", DishType.SMALL,3.4, true);
        throw new RuntimeException("teye");
    }
//    @ResponseBody
//    @RequestMapping("test1")
//    public String test(){
//        Dish dish = dishRepo.findByNameAndType("红烧排骨", DishType.STANDARD);
//        CustomQueue.push(dish,3);
//        return "success";
//    }
//
//    @ResponseBody
//    @RequestMapping("test2")
//    public String test2(){
//        Dish dish = dishRepo.findByNameAndType("红烧排骨", DishType.SMALL);
//        CustomQueue.push(dish,1);
//        return "success";
//    }
}
