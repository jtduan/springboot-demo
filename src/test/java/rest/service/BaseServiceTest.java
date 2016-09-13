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
    public void getUser(){
        Assert.assertNull("@Transactional未生效，表中找到测试数据",userRepo.findByEmail("test@qq.okm"));
    }
}