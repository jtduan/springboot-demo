package rest.util;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.VIP;
import rest.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 直接使用EntityManager处理Dao层
 */

@Repository
public class TestEntityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Deprecated
    @Transactional
    public void save(){
        User u = new User(RandomGenerator.email(),RandomGenerator.text(5),RandomGenerator.text(5),VIP.VIP1);
        entityManager.merge(u);
    }

}
