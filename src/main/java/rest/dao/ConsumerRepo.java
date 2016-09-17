package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import rest.entity.Consumer;

import javax.persistence.LockModeType;

/**
 * Created by djt on 9/17/16.
 */
public interface ConsumerRepo extends JpaRepository<Consumer,Long>{

    @Deprecated
    @Transactional
    @Modifying
    @Query("update Consumer c set c.remain = c.remain + ?2 where c.id = ?1")
    public void addOrCutRemain(long user_id, double dish_price);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Consumer c where c.id = ?1")
    public Consumer getForUpdate(long id);
}
