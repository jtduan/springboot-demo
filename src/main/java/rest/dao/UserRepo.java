package rest.dao;

import org.springframework.data.jpa.repository.Query;
import rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hero on 2016/9/5.
 */
public interface UserRepo extends JpaRepository<User,String> {
    public User findByEmail(String email);
    @Query("select u from User u where u.email = ?1 and pwd = password(?2)")
    User login(String email, String pwd);
}
