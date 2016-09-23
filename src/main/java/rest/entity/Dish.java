package rest.entity;

/**
 * Created by jtduan on 2016/9/6.
 */

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.DishType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * double本身为NotNull类型
 * Double可以为空
 *注意：此处不能加
 * @Cacheable(cacheNames = "rest.entity.Dish")
 * @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
 * 二级缓存被Spring管理，需要加在SpringDataJPA上(DishRepo)
 */
@Entity
@Table(name ="dishes")
public class Dish extends BaseEntity implements Serializable {

    @NotBlank
    @NaturalId(mutable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @NaturalId (mutable = false)
    private DishType type;

    private String description;

    private String picPath;

    private double price;

    /**
     * 评分
     */
    private double star;

    /**
     * 参与评分的人数
     */
    private int star_num;

    /**
     * 所属商家
     */

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, DishType type,double price, boolean valid,Restaurant restaurant) {
        this.name = name;
        this.type=type;
        this.price = price;
        this.valid = valid;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DishType getDishType() {
        return type;
    }

    public void setDishType(DishType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getStar_num() {
        return star_num;
    }

    public void setStar_num(int star_num) {
        this.star_num = star_num;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (type != dish.type) return false;
        return restaurant != null ? restaurant.equals(dish.restaurant) : dish.restaurant == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (restaurant != null ? restaurant.hashCode() : 0);
        return result;
    }
}
