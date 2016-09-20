package rest.other;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.DishType;
import rest.constants.VIP;
import rest.dao.DishRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    DishRepo dishRepo;

    @Test
    @Transactional
    @Rollback(true) //设置为false则不会回滚事务
    public void testTransactional() throws Exception {
        User user = new User("test@qq.okm","test","test", VIP.VIP1);
        userRepo.save(user);
    }

    @Test
    public void getUser(){
        Assert.assertFalse("@Transactional未生效，表中找到测试数据",userRepo.findByEmail("test@qq.okm").isPresent());
    }


    @Test
    public void testsaveOrUpdate() {
        Dish dish = new Dish("红烧排骨", DishType.SMALL,37,true);
        dish.setId(1);
        dish.setPrice(12);
        dishRepo.save(dish);
    }

    @Test
    public void testSet() {
        Dish dish=dishRepo.findOne(1l);
        dish.setPrice(10);
    }

}
