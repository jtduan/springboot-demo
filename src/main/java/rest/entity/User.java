package rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import rest.constants.Constant;
import rest.constants.Role;
import rest.constants.VIP;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.EnumSet;
import java.util.Set;

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

    @Any(
            metaColumn = @Column(name = "user_type", length = 8),
            fetch = FetchType.LAZY
    )
    @AnyMetaDef(
            idType = "long", metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = Consumer.class, value = "CONSUMER"),
                    @MetaValue(targetEntity = Employee.class, value = "EMPLOYEE")
            }
    )

    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property="type")
    @JsonSubTypes({@JsonSubTypes.Type(value=Employee.class,name="employee"),@JsonSubTypes.Type(value=Consumer.class,name="consumer")})
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "type_id")
    private UserType userType;

    /**
     * 有该字段才能实现级联删除User
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<LoginHistory> loginHistories;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.pwd = user.getPwd();
        this.name = user.getName();
        this.userType=user.userType;
    }

    /**
     * 消费者 构造函数
     * @param email
     * @param pwd
     * @param name
     * @param vip
     */
    public User(String email, String pwd, String name, VIP vip) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.userType=new Consumer(vip);
    }

    /**
     * 员工 构造函数
     * @param email
     * @param pwd
     * @param name
     */
    public User(String email, String pwd, String name, Role role) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.userType=new Employee(EnumSet.of(role),0);
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
