package rest.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jtduan on 2016/9/13.
 */
@Entity
@Table(name = "access_history")
public class AccessHistory extends BaseEntity{

    @ManyToOne
    private User user;

    private String ip;

    private String url;

    public AccessHistory(User user, String ip, String url) {
        this.user = user;
        this.ip = ip;
        this.url = url;
    }

    public AccessHistory() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
