package rest.module.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 注意:并没有实现有客户端向服务器发送消息的websocket代码
 */
@Service
public class NotificationService {

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    /**
     * Send notification to users subscribed on channel "/user/queue/notify".
     *
     * username 为用户id(SpringSecruity中的username)
     */
    public void notify(Notification notification, long username) {
//        messagingTemplate.convertAndSendToUser(
//                String.valueOf(username),
//                "/queue/notify",
//                notification
//        );
//        return;
    }

    public void notifyAll(Notification notification) {
//        messagingTemplate.convertAndSend(
//                "/user/queue/notify",
//                notification
//        );
//        return;
    }


    public void notifyJtduan(Notification notification) {
//        messagingTemplate.convertAndSend(
//                "/user/1/queue/notify",
//                notification
//        );
//        return;
    }

    public boolean removeOrder(long order_id) {
        return true;
    }
}
