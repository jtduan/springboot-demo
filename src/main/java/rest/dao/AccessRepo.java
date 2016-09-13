package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.RandomGenerator;
import rest.constants.VIP;
import rest.entity.AccessHistory;
import rest.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 直接使用EntityManager处理Dao层
 */
@Repository
public interface AccessRepo  extends JpaRepository<AccessHistory,Long> {
}
