package rest.entity;

import rest.constants.DishType;
import rest.constants.OrderState;

import javax.persistence.*;

/**
 * Created by jtduan on 2016/9/6.
 * 实现"快照版本",记录点餐时Dish状态
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Dish dish;

    @Enumerated(value=EnumType.STRING)
    private OrderState state;

    public Order() {
    }

    public Order(User user, Dish dish) {
        this.state = OrderState.WAITING;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}
