package rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import rest.constants.SpringUtil;
import rest.entity.Dish;

/**
 * @WithMockUser(username="admin",authorities = {"USER","ADMIN"}) 不需要真实用户
 * @WithUserDetails("jtduan@qq.com") 必须为有效用户
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DishServiceTest {

    @Autowired
    OrderService orderService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        orderService.order(SpringUtil.getEntityManager().getReference(Dish.class,1l),3);
    }

    @Test
//    @WithMockUser(username="admin",authorities = {"USER","ADMIN"})
    @WithUserDetails("jtduan@qq.com")
    public void getMessageAuthenticated() {
        orderService.order(SpringUtil.getEntityManager().getReference(Dish.class,1l),3);
    }
}
