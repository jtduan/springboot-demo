package rest.entity;

import rest.constants.DishType;
import rest.constants.OrderState;

import javax.persistence.*;

/**
 * Created by jtduan on 2016/9/6.
 * 实现"快照版本",记录点餐时Dish状态
 */
@Entity
@Table(name = "dish_order")
public class Order extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String dish_name;

    @Enumerated(value=EnumType.STRING)
    private DishType dish_type;

    private double dish_price;

    @Enumerated(value=EnumType.STRING)
    private OrderState state;

    public Order() {
    }

    public Order(User user, Dish dish) {
        this.dish_name=dish.getName();
        this.dish_type = dish.getDishType();
        this.dish_price = dish.getPrice();
        this.state = OrderState.WAITING;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public DishType getDish_type() {
        return dish_type;
    }

    public void setDish_type(DishType dish_type) {
        this.dish_type = dish_type;
    }

    public double getDish_price() {
        return dish_price;
    }

    public void setDish_price(double dish_price) {
        this.dish_price = dish_price;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}
