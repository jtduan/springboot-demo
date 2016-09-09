package rest.entity;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Role;
import rest.constants.VIP;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by hero on 2016/9/5.
 * 不要使用Email作为主键，不方便修改绑定
 * 添加Unique约束 uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
 */
@Entity
@Table(name ="user")
public class User extends BaseEntity{
    @Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$",message = "必须为一个合法的Email地址")
    @NaturalId(mutable = true)
    private String email;

    @NotBlank
    @ColumnTransformer(write = "password(?)")
    private String pwd;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private VIP vip;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserFund fund;

    public User() {
    }

    public User(String email, String pwd, String name, VIP vip) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.vip = vip;
        this.fund = new UserFund(this,0,0);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VIP getVip() {
        return vip;
    }

    public void setVip(VIP vip) {
        this.vip = vip;
    }

    public UserFund getFund() {
        return fund;
    }

    public void setFund(UserFund fund) {
        this.fund = fund;
    }
}
