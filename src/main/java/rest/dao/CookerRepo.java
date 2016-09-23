package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import rest.entity.Cooker;
import rest.entity.User;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Created by hero on 2016/9/5.
 */
public interface CookerRepo extends JpaRepository<Cooker, Long> {
    Optional<Cooker> findById(long id);
}
