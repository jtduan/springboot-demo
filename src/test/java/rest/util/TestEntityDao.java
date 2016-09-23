package rest.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.RandomGenerator;
import rest.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 直接使用EntityManager处理Dao层
 */

@Repository
public class EntityDao {

    @PersistenceContext
    private EntityManager entityManager;
}
