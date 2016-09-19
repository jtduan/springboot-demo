package rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.backthreads.Waitress;
import rest.constants.CurrentUserUtils;
import rest.constants.OrderState;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;
import rest.controller.UserController;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    /**
     * 悲观锁，2次连续的order不会造成数据不一致
     * 注意，参数u 属于游离态，无法更新到数据库，需要getReference
     * @param u
     * @param id
     * @return
     */
    @PreAuthorize("authenticated")
    public ResponseType order(Long u, long id){
        try {
            if (!(SpringUtil.checkUser(u) || SpringUtil.checkAdmin())) {
                return ResponseType.PERMISSION_DENIED;
            }
            Dish dish = dishRepo.findOne(id);
            if (dish == null) {
                return ResponseType.NOTEXIST_DISH;
            }
            Order o = Waitress.INSTANCE.add(userRepo.getOne(u), dish);
            notificationService.notify(new Notification("add", o), u);
            return ResponseType.SUCCESS;
        }catch (NoMoneyException e){
            return ResponseType.NO_SUFFICIENT_FUND ;
        }catch (Exception e){
            return ResponseType.FATAL;
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

    @Transactional
    public Order add(User u,Dish dish) {
        Order order = new Order(u,dish);
        cost(order.getUser(),order.getDish_price());
        return orderRepo.saveAndFlush(order);
    }

    /**
     * 此处有BUG:两次更新可能会出现数据不一致
     * @param user
     * @param dish_price
     */
    private void cost(User user, double dish_price) {
        if(user.getUserType() instanceof Consumer){
            Consumer u = consumerRepo.getForUpdate(((Consumer) user.getUserType()).getId());
            if(u.getRemain() - dish_price<0){
                throw new NoMoneyException("余额不足");
            }
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
     * 疑问：此处不能直接操作order做修改并保存，为什么？(数据库会保存两条记录)
     * 答案：1、事务提交时才会执行sql,
     * 点餐时，事务1，生成order（insert语句）->加入队列->通知厨师->厨师通知更新-> 事务提交（有时检测到order属性变化，生成update语句，有时未检测到,未更新）
     *  厨师线程事务2：收到更新通知->执行更新（事务1未提交，生成insert语句）->提交
     *
     *  一个事务插入数据时，mysql会加锁（间隙锁），另一事务也执行插入时将等待（如果是主键自增，则同一表的插入需轮流进行），由于主键自增，
     *  hibernate生成insert语句时不会带id字段，hibernate在事务提交前也会检测entity状态，因此共享的entity插入会产生令人迷惑的sql
     *
     * 两个事务交叉save同一游离态对象时，可能出现问题，解决方案是缩小Transactional作用范围
     * 注意：flush方法并不是直接保存到数据库，而仅仅是在事务提交时一定将执行flush需要的sql语句
     * @param order
     */
    @Transactional
    public void cooking(Order order) {
        order.setState(OrderState.COOKING);
        notificationService.notify(new Notification("update",order),order.getUser().getId());
        orderRepo.save(order);
    }

    public List<Order> findNotFinishedOrders(Long u) {
        User user = userRepo.getOne(u);
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
