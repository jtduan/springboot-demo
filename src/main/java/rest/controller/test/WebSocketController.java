package rest.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import rest.constants.ResponseType;
import rest.module.websocket.Notification;
import rest.module.websocket.SystemWebSocketHandler;

/**
 * @author jtduan
 * @date 2016/9/23
 */
@Controller
@RequestMapping("/test/websocket")
public class WebSocketController {

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    @RequestMapping(value = "",produces = "text/x-responseType")
    @ResponseBody
    public ResponseType listDishes(){
        systemWebSocketHandler.sendMessageToUser("1", new TextMessage(new Notification("测试消息").getContent()));
        return ResponseType.SUCCESS;
    }
}
