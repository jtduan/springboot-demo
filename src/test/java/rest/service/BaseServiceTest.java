package rest.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.VIP;
import rest.dao.UserRepo;
import rest.entity.User;

/**
 * 与数据库操作相关的测试都需要加上@Transcational注解
 * 不仅仅可以回滚事务，部分测试去掉注解会测试失败
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    BaseService service;

    @Autowired
    UserRepo userRepo;

    @Test
    @Transactional
    @Rollback(true) //设置为false则不会回滚事务
    public void testInitDataBase() throws Exception {
    }

    @Test
    @Transactional
    @Rollback(true) //设置为false则不会回滚事务
    public void testTransactional() throws Exception {
        User user = new User("test@qq.okm","test","test", VIP.VIP1);
        userRepo.save(user);
    }
    @Test
    public void testTransactionalRes(){
        Assert.assertNull("@Transactional未生效，表中找到测试数据",userRepo.findByEmail("test@qq.okm"));
    }

    @Test
    @Transactional
    public void getUser(){
        User u=service.InsertRandomUser();
        Assert.assertTrue(u.getId()>0);
        Assert.assertNull("@Transactional未生效，表中找到测试数据",userRepo.findByEmail("test@qq.okm"));
    }

}
