package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Constant;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * Created by hero on 2016/9/5.
 * 不要使用Email作为主键，不方便修改绑定
 * 添加Unique约束 uniqueConstraints = {@UniqueConstraint(columnNames = "email")}
 */

@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Pattern(regexp = Constant.emailPattern, message = "必须为一个合法的Email地址")
    @NaturalId(mutable = true)
    private String email;

    @JsonIgnore
    @NotBlank
    @ColumnTransformer(write = "md5(?)")
    private String pwd;

    private String name;

    public User() {
    }

    public User(User u) {
        this.id = u.id;
        this.email = u.email;
        this.pwd = u.pwd;
        this.name = u.name;
    }
    /**
     * 消费者 构造函数
     * @param email
     * @param pwd
     * @param name
     */
    public User(String email, String pwd, String name) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
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
}
