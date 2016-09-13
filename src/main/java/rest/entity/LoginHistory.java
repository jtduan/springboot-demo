package rest.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jtduan on 2016/9/6.
 */
@Entity
@Table(name = "login_history")
public class LoginHistory{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    @ManyToOne
    private User user;

    private String user_name;

    private String pwd;

    private String ip;

    private boolean res;

    private LocalDateTime login_time;

    public LoginHistory(User user,String ip) {
        this.ip=ip;
        this.user=user;
        this.res=true;
    }

    public LoginHistory(String username,String pwd,String ip) {
        this.ip=ip;
        this.user_name=username;
        this.pwd=pwd;
        this.res=false;
    }

    public LoginHistory() {
    }

    @PrePersist
    public void createTime(){
        login_time = LocalDateTime.now();
    }
}
