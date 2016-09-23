package rest.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author jtduan
 * @date 2016/9/23
 * 注意：使用TreeSet需要实现Comparable接口
 */
@Entity
@Table(name ="restaurant")
public class Restaurant extends BaseEntity{

    /**
     * 商家名称
     */
    @NaturalId(mutable = false)
    private String name;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cooker> cookers;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Dish> dishes;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Adminer> adminers;

    /**
     * 商家在系统中的余额，用于提现
     */
    private double ramain;

    /**
     * 已提现金额
     */
    private double withdraw;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
        this.cookers = new ArrayList<>();
        this.dishes = new ArrayList<>();
        this.adminers = new ArrayList<>();
    }

    public Restaurant(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRamain() {
        return ramain;
    }

    public void setRamain(double ramain) {
        this.ramain = ramain;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }

    public List<Cooker> getCookers() {
        return cookers;
    }

    public void setCookers(List<Cooker> cookers) {
        this.cookers = cookers;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Adminer> getAdminers() {
        return adminers;
    }

    public void setAdminers(List<Adminer> adminers) {
        this.adminers = adminers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
