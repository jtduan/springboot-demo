package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by jtduan on 2016/9/6.
 *  @JsonIgnore 会使构造json时忽略该字段（以免造成死循环）
 */
@Entity
@Table(name ="user_fund")
public class UserFund extends BaseEntity{

    @Id
    private long id;

    @JsonIgnore
    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    /**
     * 余额
     */
    private double remain;

    /**
     * 历史消费
     */
    private double cost;

    public UserFund() {
    }

    public UserFund(User user, double remain, double cost) {
        this.user = user;
        this.remain = remain;
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
