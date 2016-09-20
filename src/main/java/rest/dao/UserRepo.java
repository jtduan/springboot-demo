package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import rest.entity.User;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Created by hero on 2016/9/5.
 */
public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    /**
     * 使用此种方式会饶过hibernate validation
     *
     * @param id
     * @param username
     */
    @Transactional
    @Modifying
    @Query("update User u set u.name=?2 where u.id=?1")
    public void updateUsername(long id, String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id=?1")
    public User getForUpdate(long id);

    @Query("select count(u)>0 from User u where u.email=?1")
    public boolean existsByEmail(String email);
}
