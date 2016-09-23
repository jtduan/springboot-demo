package rest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
public abstract class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected long id;

//    @Column(insertable = false,updatable = false)
//    @Generated(value = GenerationTime.ALWAYS)
//    注意:generated注解表示 hibernate会在获取实体的时候进行刷新(以读取正确的updateTime),而不是update的时候自动添加该字段值
    @Column(updatable=false)
    private LocalDateTime createTime;

    @NotNull
    protected boolean valid;

    @Version
    private long version;

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @PrePersist
    public void createTime(){
        createTime = LocalDateTime.now();
    }
}
