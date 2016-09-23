package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Constant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name ="adminers")
public class Adminer extends BaseEntity{

    @Pattern(regexp = Constant.emailPattern, message = "必须为一个合法的Email地址")
    @NaturalId(mutable = true)
    private String email;

    @JsonIgnore
    @NotBlank
    @ColumnTransformer(write = "md5(?)")
    private String pwd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Restaurant restaurant;

    public Adminer() {
    }

    public Adminer(String email, String pwd,Restaurant restaurant) {
        this.email = email;
        this.pwd = pwd;
        this.restaurant = restaurant;
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
