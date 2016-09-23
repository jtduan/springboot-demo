package rest.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.RandomGenerator;
import rest.constants.ResponseType;
import rest.controller.BaseControllerTest;

/**
 * @WithMockUser(username="admin",authorities = {"USER","ADMIN"}) 不需要真实用户
 * @WithUserDetails("jtduan@qq.com") 必须为有效用户
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DishServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    private UserService userService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        orderService.order(RandomGenerator.getRandomUser().getId(),1);
    }

    @Test
    @Transactional
    @WithUserDetails("jtduan@qq.com")
    public void testOrder() {
        ResponseType responseType =  orderService.order(userService.findbyEmail("jtduan@qq.com").getId(),3);
        Assert.assertEquals("消费者点单",ResponseType.SUCCESS,responseType);
    }

//    @Test
//    @Transactional
//    @WithUserDetails("jtduan@qq.com")
//    public void testOrder2() {
//        ResponseType responseType = orderService.order(BaseControllerTest.baseService.getTestUser2().getId(),3);
//        Assert.assertEquals("没有权限点别人的单",ResponseType.PERMISSION_DENIED,responseType);
//    }
}
