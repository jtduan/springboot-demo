package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.entity.LoginHistory;

/**
 * 直接使用EntityManager处理Dao层
 */
@Repository
public interface LoginHistoryRepo extends JpaRepository<LoginHistory,Long> {
}
