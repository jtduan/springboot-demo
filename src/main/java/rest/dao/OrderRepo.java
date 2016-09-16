package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import rest.constants.OrderState;
import rest.entity.Order;
import rest.entity.User;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by hero on 2016/9/5.
 */
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("select o from Order o where o.user = ?1 and (o.state=?2 or o.state=?3)")
    public List<Order> findByUserAndStatus(User u, OrderState status, OrderState status2);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select o from Order o where o.id = ?1")
    public Order getForUpdate(long id);
}
