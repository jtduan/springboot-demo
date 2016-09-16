package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.constants.ResponseType;
import rest.dao.DishRepo;
import rest.entity.Dish;
import rest.module.websocket.Notification;
import rest.module.websocket.NotificationService;

import javax.annotation.security.PermitAll;

/**
 * Created by jtduan on 2016/9/6.
 */

@Deprecated
@Controller
@RequestMapping("/base")
public class TestController {

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("message_converter")
    public String message_convert(){
        return "base/message_converter";
    }

    @RequestMapping(value = "convertEnumToStr", produces = { "text/x-responseType" })
    @ResponseBody
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

    @RequestMapping(value = "/websocket", method = RequestMethod.GET)
    public String index() {
        return "base/websocket/websocket_client";
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> notifyOneUser() {
        notificationService.notify(
                new Notification("hello"), // notification object
                1                    // username
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/notifyAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> notifyAllUser() {
        notificationService.notifyAll(
                new Notification("hello,everyone!") // notification object
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
