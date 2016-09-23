package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.EnumSet;

@Entity
@Table(name ="cookers")
public class Cooker extends BaseEntity{

    private String name;

    @JsonIgnore
    @NotBlank
    @ColumnTransformer(write = "md5(?)")
    private String pwd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Restaurant restaurant;

    public Cooker() {
    }

    public Cooker(Cooker cooker) {
        this.name = cooker.name;
        this.pwd = cooker.pwd;
        this.restaurant = cooker.restaurant;
    }

    public Cooker(String name, String pwd,Restaurant restaurant) {
        this.name = name;
        this.pwd = pwd;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
