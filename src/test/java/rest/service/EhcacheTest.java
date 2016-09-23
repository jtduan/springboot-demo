package rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rest.dao.DishRepo;
import rest.entity.Dish;

/**
 * @author jtduan
 * @date 2016/9/22
 * findOne()不会从缓存中读取,读取结果也不会进入缓存
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EhcacheTest {
    @Autowired
    private DishRepo dishRepo;

    /**
     * 测试findById是否能够从缓存查询
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        Dish d1 = dishRepo.findById(1l);
        System.out.println("第一次查询：" + d1.getName());

        Dish d2 = dishRepo.findById(1l);
        System.out.println("第二次查询：" + d2.getName());
    }

    /**
     * 测试findOne是否能够从缓存查询，结论：未缓存
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Dish d1 = dishRepo.findById(1l);
        System.out.println("第一次查询：" + d1.getName());

        Dish d2 = dishRepo.findOne(1l);
        System.out.println("第二次查询：" + d2.getName());
    }

    /**
     * 测试findOne是否进行缓存，结论：未缓存
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Dish d1 = dishRepo.findOne(1l);
        System.out.println("第一次查询：" + d1.getName());

        Dish d2 = dishRepo.findById(1l);
        System.out.println("第二次查询：" + d2.getName());
    }
}
