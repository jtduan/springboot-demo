package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import rest.entity.Consumer;
import rest.entity.Employee;

import javax.persistence.LockModeType;

/**
 * Created by djt on 9/17/16.
 */
public interface EmployeeRepo extends JpaRepository<Employee,Long>{

    /**
     * 该函数会绕过hibernate-validate校验，不推荐使用
     * @param user_id
     * @param dish_price
     */
    @Deprecated
    @Transactional
    @Modifying
    @Query("update Employee e set e.salary = e.salary + ?2 where e.id = ?1")
    public void addOrCutRemain(long user_id, double dish_price);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Employee e where e.id = ?1")
    public Employee getForUpdate(long id);
}
