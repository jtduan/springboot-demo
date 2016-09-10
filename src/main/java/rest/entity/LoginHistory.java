package rest.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by jtduan on 2016/9/6.
 */
@Entity
@Table(name = "login_history")
public class LoginHistory extends BaseEntity{

    @ManyToOne
    User user;

    private String ip;

}
