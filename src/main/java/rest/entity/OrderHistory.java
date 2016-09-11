package rest.entity;

import rest.constants.DishType;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by jtduan on 2016/9/6.
 * 实现"快照版本",记录点餐时Dish状态
 */
@Entity
@Table(name = "order_history")
public class OrderHistory extends BaseEntity{

    @ManyToOne
    private User user;

    private String dish_name;

    private DishType dish_type;

    private double dish_price;

    /**
     * 状态,已取消-1/等待中0/正在做1/已完成2/
     */
    private int state;

    public OrderHistory(User user,Dish dish) {
        this.dish_name=dish.getName();
        this.dish_type = dish.getType();
        this.dish_price = dish.getPrice();
        this.state = 0;
        this.user = user;
    }
}
