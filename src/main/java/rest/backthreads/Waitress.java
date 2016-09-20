package rest.backthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.ResponseType;
import rest.controller.UserController;
import rest.entity.Dish;
import rest.entity.Order;
import rest.entity.User;
import rest.module.websocket.Notification;
import rest.module.websocket.NotificationService;
import rest.service.OrderService;

import javax.annotation.PostConstruct;

/**
 * Created by jtduan on 2016/9/6.
 * 单例模式 enum实现
 * 注意:单例模式不能加@Component和@Autowired注解,可以通过这种方式实现注入效果
 */
public enum Waitress {
    INSTANCE;

    private NotificationService notificationService;

    private OrderService orderService;

    private static final Logger log = LoggerFactory.getLogger(Waitress.class);

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyUser(Order order){
        orderService.finish(order);
        notificationService.notify(new Notification("菜品已完成:"+order.getDish_name()),order.getUser().getId());
        log.info("通知："+order.getUser().getEmail()+"-"+order.getDish_name());
    }

    public Order add(User u,Dish dish){
        Order order = orderService.add(u,dish);
        CustomQueue.INSTANCE.push(order);
        return order;
    }

    public synchronized ResponseType remove(Order order){
        CustomQueue.INSTANCE.remove(order);
        ResponseType responseType = orderService.setCancel(order.getId());
        if(ResponseType.SUCCESS.equals(responseType)) {
            notificationService.notify(new Notification("update", order), order.getUser().getId());
        }
        return responseType;
    }

    public ResponseType press(Order order){
        return CustomQueue.INSTANCE.press(order);
    }

    public void notifyUser(WaitedDish waitedDish) {
        for(Order order:waitedDish.orders){
            notifyUser(order);
        }
    }

    public synchronized WaitedDish notifyCooking() {
        WaitedDish waitedDish = CustomQueue.INSTANCE.poll();
        if(waitedDish == null) return null;
        for(Order order:waitedDish.orders){
            notifyCooking(order);
        }
        return waitedDish;
    }

    public void notifyCooking(Order order){
        orderService.cooking(order);
        notificationService.notify(new Notification("菜品已开始烹饪:"+order.getDish_name()),order.getUser().getId());
        log.info("通知(菜品已开始烹饪)："+order.getUser().getEmail()+"-"+order.getDish_name());
    }
}
@Component
class WaitressInjector{

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderService orderService;


    @PostConstruct
    public void postConstruct() {
        Waitress.INSTANCE.setNotificationService(notificationService);
        Waitress.INSTANCE.setOrderService(orderService);
    }
}
