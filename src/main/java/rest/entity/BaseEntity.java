package rest.entity;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by jtduan on 2016/9/9.
 *   @PrePersist
 *   @PreRemove
 *   @PostPersist
 *   @PostRemove
 *   @PreUpdate
 *   @PostUpdate
 *   @PostLoad
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    @Column(updatable=false)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @NotNull
    protected boolean valid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @PrePersist
    public void createTime(){
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateTime(){
        updateTime = LocalDateTime.now();
    }
}
