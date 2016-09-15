package rest.entity;

/**
 * Created by jtduan on 2016/9/6.
 */

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.DishType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * double本身为NotNull类型
 * Double可以为空
 */
@Entity
@Table(name ="dishes")
public class Dish extends BaseEntity implements Serializable {

    @NotBlank
    @NaturalId (mutable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @NaturalId (mutable = false)
    private DishType type;

    private double price;

    /**
     * 评分
     */
    private double star;

    /**
     * 参与评分的人数
     */
    private int star_num;

    public Dish() {
    }

    public Dish(String name, DishType type,double price, boolean valid) {
        this.name = name;
        this.type=type;
        this.price = price;
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DishType getUserType() {
        return type;
    }

    public void setUserType(DishType type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        return type == dish.type;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
