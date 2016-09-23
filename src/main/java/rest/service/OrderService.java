package rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import rest.constants.Constant;
import rest.constants.OrderState;
import rest.constants.ResponseType;
import rest.dao.DishRepo;
import rest.dao.OrderRepo;
import rest.dao.UserRepo;
import rest.entity.Order;
import rest.entity.User;

import java.util.stream.LongStream;

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

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    /**
     * 点餐，仅仅保存到数据库
     *
     * @param user
     * @param ids
     * @return
     */
    @PreAuthorize("authenticated")
    public ResponseType order(String user, long[] ids) {
        User u = getUser(user);
        LongStream.of(ids).forEach((id) -> {
            Order o = dishRepo.findById(id).map((dish) -> {
                return new Order(u, dish);
            }).get();
            orderRepo.save(o);
        });
        return ResponseType.SUCCESS;
    }

    private User getUser(String u) {
        if (u.startsWith(Constant.UserPrefix)) {
            if (u.substring(Constant.UserPrefix.length()).matches("[0-9]+")) {
                return userRepo.findById(Long.parseLong(u.substring(Constant.UserPrefix.length())))
                        .orElseThrow(ResponseType.USER_NOTFOUND::getException);
            } else {
                throw ResponseType.USER_NOTFOUND.getException();
            }
        }
        return null;
    }

    /**
     * 取消点餐，使用AOP进行通知
     *
     * @param user
     * @param ids
     * @return
     */
    @PreAuthorize("authenticated")
    public ResponseType cancel(long order_id) {
        orderRepo.setState(order_id, OrderState.CANCELED);
        return ResponseType.SUCCESS;
    }
}
