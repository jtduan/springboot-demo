package rest.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by jtduan on 2016/9/6.
 * 实现"快照版本",记录点餐时Dish状态
 */
@Entity
@Table(name = "order_history")
public class OrderHistory extends Dish{

    @ManyToOne
    private User user;

    /**
     * 状态,已取消/正在做/已完成/
     */
    private int state;
}
