package rest.dao;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.ResponseType;
import rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by hero on 2016/9/5.
 */
public interface UserRepo extends JpaRepository<User,Long> {
    public User findByEmail(String email);

    /**
     * 使用此种方式会饶过hibernate validation
     * @param id
     * @param username
     */
    @Transactional
    @Modifying
    @Query("update User u set u.name=?2 where u.id=?1")
    public void updateUsername(long id,String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id=?1")
    User getForUpdate(long id);

}
