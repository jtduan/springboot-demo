package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.backthreads.Waitress;
import rest.constants.OrderState;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;
import rest.dao.*;
import rest.entity.*;
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
    private EmployeeRepo employeeRepo;

    @Autowired
    private ConsumerRepo consumerRepo;

    @Autowired
    private NotificationService notificationService;

    /**
     * 悲观锁，2次连续的order不会造成数据不一致
     * 注意，参数u 属于游离态，无法更新到数据库，需要getReference
     * @param u
     * @param id
     * @return
     */
    @PreAuthorize("authenticated")
    @Transactional
    public ResponseType order(User u, long id){
        try {
            if (!(SpringUtil.checkUser(u.getId()) || SpringUtil.checkAdmin())) {
                return ResponseType.PERMISSION_DENIED;
            }
            Dish dish = dishRepo.findOne(id);
            if (dish == null) {
                return ResponseType.NOTEXIST_DISH;
            }
            Order o = Waitress.INSTANCE.add(userRepo.getOne(u.getId()), dish);
            notificationService.notify(new Notification("add", o), u.getId());
            return ResponseType.SUCCESS;
        }catch (Exception e){
            return ResponseType.NO_SUFFICIENT_FUND ;
        }
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
        cost(order.getUser(),order.getDish_price());
        return orderRepo.save(order);
    }

    /**
     * 此处有BUG:两次更新可能会出现数据不一致
     * @param user
     * @param dish_price
     */
    private void cost(User user, double dish_price) {
        if(user.getUserType() instanceof Consumer){
            Consumer u = consumerRepo.getForUpdate(((Consumer) user.getUserType()).getId());
            u.setRemain(u.getRemain() - dish_price);
            u.setCost(u.getCost() + dish_price);
            consumerRepo.save(u);
        }
        else if(user.getUserType() instanceof Employee){
            Employee e = employeeRepo.getForUpdate(((Employee) user.getUserType()).getId());
            e.setSalary(e.getSalary()- dish_price);
            employeeRepo.save(e);
        }
    }

    /**
     * 该方法父级被synchornized修饰，不会出现多线程并发的情况
     * 疑问：此处不能加orderRepo.save(order);会产生两条记录，为什么？(貌似启动服务器首次运行会这样）
     * @param order
     */
    @Transactional
    public void cooking(Order order) {
        order.setState(OrderState.COOKING);
        notificationService.notify(new Notification("update",order),order.getUser().getId());
//        orderRepo.save(order);
    }

    public List<Order> findNotFinishedOrders(User u) {
        User user = userRepo.getOne(u.getId());
        return orderRepo.findByUserAndStatus(user,OrderState.WAITING,OrderState.COOKING);
    }

    public ResponseType press(long id) {
        Order order = orderRepo.findOne(id);
        if(!(SpringUtil.checkUser(order.getUser().getId())||SpringUtil.checkAdmin())){
            return ResponseType.PERMISSION_DENIED;
        }
        return Waitress.INSTANCE.press(order);
    }

    @Transactional
    public ResponseType cancel(long id) {
        Order order = orderRepo.findOne(id);
        if(!(SpringUtil.checkUser(order.getUser().getId())||SpringUtil.checkAdmin())){
            return ResponseType.PERMISSION_DENIED;
        }
        return Waitress.INSTANCE.remove(order);
    }

    /**
     * 悲观锁，不会出现2次数据库表的更新（第二次state已经为CANCELED)
     * @param id
     * @return
     */
    @Transactional
    public ResponseType setCancel(long id) {
        Order order = orderRepo.getForUpdate(id);
        if(order.getState().equals(OrderState.WAITING)){
            order.setState(OrderState.CANCELED);
            orderRepo.save(order);
            cancel(order.getUser(),order.getDish_price());
            return ResponseType.SUCCESS;
        }
        return ResponseType.ORDER_DOING;
    }

    private void cancel(User user, double dish_price) {
        if(user.getUserType() instanceof Consumer){
            Consumer u = consumerRepo.getForUpdate(((Consumer) user.getUserType()).getId());
            u.setRemain(u.getRemain() + dish_price);
            u.setCost(u.getCost() - dish_price);
            consumerRepo.save(u);
        }
        else if(user.getUserType() instanceof Employee){
            Employee e = employeeRepo.getForUpdate(((Employee) user.getUserType()).getId());
            e.setSalary(e.getSalary()+ dish_price);
            employeeRepo.save(e);
        }
    }

    private void addRemain(User user, double dish_price) {
        if(user.getUserType() instanceof Consumer){
            Consumer u = consumerRepo.getForUpdate(((Consumer) user.getUserType()).getId());
            u.setRemain(u.getRemain()+ dish_price);
            consumerRepo.save(u);
        }
        else if(user.getUserType() instanceof Employee){
            Employee e = employeeRepo.getForUpdate(((Employee) user.getUserType()).getId());
            e.setSalary(e.getSalary()+ dish_price);
            employeeRepo.save(e);
        }
    }
}
