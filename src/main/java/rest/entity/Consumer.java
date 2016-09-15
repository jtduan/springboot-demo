package rest.entity;

import rest.constants.VIP;

import javax.persistence.*;

/**
 * Created by jtduan on 2016/9/6.
 *  @JsonIgnore 会使构造json时忽略该字段（以免造成死循环）
 */
@Entity
@Table(name ="consumer")
@DiscriminatorValue("CONSUMER")
public class Consumer extends BaseEntity implements UserType{

    @Enumerated(EnumType.STRING)
    private VIP vip;

    /**
     * 余额
     */
    private double remain;

    /**
     * 历史消费
     */
    private double cost;

    public Consumer() {
    }

    public Consumer(VIP vip) {
        this(0,0,vip);
    }

    public Consumer(double remain, double cost, VIP vip) {
        this.remain = remain;
        this.cost = cost;
        this.vip=vip;
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

    public VIP getVip() {
        return vip;
    }

    public void setVip(VIP vip) {
        this.vip = vip;
    }
}
