package rest.backthreads;

import rest.constants.DishType;
import rest.entity.Order;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by jtduan on 2016/9/6.
 */
public class WaitedDish {
    /**
     * 菜品
     */
    public String dish_name;

    public Queue<Order> orders = new ArrayDeque<Order>();

    /**
     * 进入队列时间
     */
    public LocalDateTime in_time;

    public WaitedDish(Order order) {
        this.dish_name=order.getDish_name();
        this.orders.add(order);
        this.in_time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaitedDish that = (WaitedDish) o;

        return !(dish_name != null ? !dish_name.equals(that.dish_name) : that.dish_name != null);

    }

    @Override
    public int hashCode() {
        return dish_name != null ? dish_name.hashCode() : 0;
    }
}
