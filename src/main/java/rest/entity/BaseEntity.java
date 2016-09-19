package rest.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected long id;

    @Column(updatable=false)
    private LocalDateTime createTime;

//    @Column(insertable = false,updatable = false)
//    @Generated(value = GenerationTime.ALWAYS)
//    注意:generated注解表示 hibernate会在获取实体的时候进行刷新(以读取正确的updateTime),而不是update的时候自动添加该字段值
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
