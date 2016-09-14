package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Constant;
import rest.constants.ResponseType;
import rest.constants.Role;
import rest.constants.VIP;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by hero on 2016/9/5.
 * 不要使用Email作为主键，不方便修改绑定
 * 添加Unique约束 uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
 */

@Entity
@Table(name ="user")
public class User extends BaseEntity{
    @Pattern(regexp = Constant.emailPattern,message = "必须为一个合法的Email地址")
    @NaturalId(mutable = true)
    private String email;

    @JsonIgnore
    @NotBlank
    @ColumnTransformer(write = "md5(?)")
    private String pwd;

    private String name;

    @Enumerated(EnumType.STRING)
    private VIP vip;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private UserFund fund;

    @Convert(converter = RolesConverter.class)
    private EnumSet<Role> roles;

    /**
     * 有该字段才能实现级联删除User
     */
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<LoginHistory> loginHistories;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.pwd = user.getPwd();
        this.name = user.getName();
        this.vip = user.getVip();
        this.fund = user.getFund();
        this.roles = user.getRoles();
    }


    public User(String email, String pwd, String name, VIP vip) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.vip = vip;
        this.roles=EnumSet.of(Role.USER);
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

    public EnumSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(EnumSet<Role> roles) {
        this.roles = roles;
    }
}
