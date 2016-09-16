package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.backthreads.Waitress;
import rest.constants.OrderState;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;
import rest.dao.DishRepo;
import rest.dao.OrderRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.Order;
import rest.entity.User;
import rest.module.websocket.Notification;
import rest.module.websocket.NotificationService;

import java.util.List;

/**
 * Created by jtduan on 2016/9/8.
 */
@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private NotificationService notificationService;

    @PreAuthorize("authenticated")
    public ResponseType order(User u, long id){
        if(!(SpringUtil.checkUser(u.getId())||SpringUtil.checkAdmin())){
            return ResponseType.PERMISSION_DENIED;
        }
        Dish dish = dishRepo.findOne(id);
        if(dish==null){
            return ResponseType.NOTEXIST_DISH;
        }
        Order o = Waitress.INSTANCE.add(u,dish);
        notificationService.notify(new Notification("add",o),u.getId());
        return ResponseType.SUCCESS;
    }

    private Order save(Order order){
        return orderRepo.save(order);
    }

    public void finish(Order order) {
        order.setState(OrderState.FINISHED);
        notificationService.notify(new Notification("update",order),order.getUser().getId());
        orderRepo.save(order);
    }

    public Order add(User u,Dish dish) {
        Order order = new Order(u,dish);
        return orderRepo.save(order);
    }

    public void cooking(Order order) {
        order.setState(OrderState.COOKING);
        notificationService.notify(new Notification("update",order),order.getUser().getId());
        orderRepo.save(order);
    }

    public List<Order> findNotFinishedOrders(User u) {
        User user =userRepo.getOne(u.getId());
        return orderRepo.findByUserAndStatus(user,OrderState.WAITING,OrderState.COOKING);
    }

    public ResponseType press(long id) {
        Order order = orderRepo.findOne(id);
        if(!(SpringUtil.checkUser(order.getUser().getId())||SpringUtil.checkAdmin())){
            return ResponseType.PERMISSION_DENIED;
        }
        return Waitress.INSTANCE.press(order);
    }

    public ResponseType cancel(long id) {
        Order order = orderRepo.findOne(id);
        if(!(SpringUtil.checkUser(order.getUser().getId())||SpringUtil.checkAdmin())){
            return ResponseType.PERMISSION_DENIED;
        }
        return Waitress.INSTANCE.remove(order);
    }

    @Transactional
    public ResponseType setCancel(long id) {
        Order order = orderRepo.getForUpdate(id);
        if(order.getState().equals(OrderState.WAITING)){
            order.setState(OrderState.CANCELED);
            orderRepo.save(order);
            return ResponseType.SUCCESS;
        }
        return ResponseType.ORDER_DOING;
    }
}
