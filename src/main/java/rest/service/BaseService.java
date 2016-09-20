package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.DishType;
import rest.constants.Role;
import rest.constants.SpringUtil;
import rest.constants.VIP;
import rest.dao.ConsumerRepo;
import rest.dao.DishRepo;
import rest.dao.OrderRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.User;

import java.util.List;

/**
 * Created by jtduan on 2016/9/6.
 */
@Service
public class BaseService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ConsumerRepo consumerRepo;

    private static Dish d = new Dish();
    /**
     * Todo：使用hibernate batch完成大量数据插入
     */
    public void initDataBase() {
        User user = new User("jtduan@qq.com", "jtduan", "jtduan", VIP.VIP0);
        if (!userRepo.existsByEmail("jtduan@qq.com")) {
            user.getUserType().recharge(12888);
            userRepo.save(user);
        }

        User user2 = new User("jtduan2@qq.com", "jtduan", "jtduan2", VIP.VIP1);
        if (!userRepo.existsByEmail("jtduan2@qq.com")) {
            user.getUserType().recharge(12888);
            userRepo.save(user2);
        }

        Dish dish = new Dish("红烧排骨", DishType.SMALL, 37, true);
        if (!dishRepo.existByNameAndType("红烧排骨", DishType.SMALL)) dishRepo.save(dish);

        dish = new Dish("红烧排骨", DishType.STANDARD, 46, true);
        if (!dishRepo.existByNameAndType("红烧排骨", DishType.STANDARD)) dishRepo.save(dish);

        dish = new Dish("红烧排骨", DishType.BIG, 55, true);
        if (!dishRepo.existByNameAndType("红烧排骨", DishType.BIG)) dishRepo.save(dish);
    }

    @Transactional
    public User getTestUser() {
        return userRepo.findByEmail("jtduan@qq.com").get();
    }

    @Transactional
    public long getTestUserId() {
        return userRepo.findByEmail("jtduan@qq.com").get().getId();
    }

    @Transactional
    public User getTestUser2() {
        return userRepo.findByEmail("jtduan2@qq.com").get();
    }

    @Transactional
    public long getTestUser2Id() {
        return userRepo.findByEmail("jtduan2@qq.com").get().getId();
    }

    @Transactional
    public void updateUser() throws InterruptedException {
        User u = userRepo.getForUpdate(1);
        Thread.sleep(100000);
        u.getUserType().recharge(19);
        userRepo.save(u);
    }

    @Transactional
    public void updateUserFund() throws InterruptedException {
        consumerRepo.addOrCutRemain(1, 18);
    }

    @Transactional
    public void set() {
        Dish dish = dishRepo.findOne(1l);
        dish.setPrice(10);
    }

    @Transactional
    public User get() {
        User u = userRepo.findOne(1l);
        return u;
    }

    /**
     * 模拟点餐bug
     * 结论：merge函数并非一定update流程：
     * 首先会查看主键id是否为空，
     1.要是为空的话那就直接执行insert语句将entity持久化，结束。
     2.要是不为空，那么先执行select的语句查询entity表中id为这个entity.id的记录是否存在
     A:存在，那好执行update语句，结束。
     B：不存在，那么执行inset语句，结束。
     * @return
     * @throws InterruptedException
     */
    @Transactional
    public void trans1() throws InterruptedException {
        d.setPrice(8.8);
        d.setName("测试菜品");
        d.setDishType(DishType.SMALL);
//        d = dishRepo.save(d);
        SpringUtil.getEntityManager().merge(d);
        Thread.sleep(1000*10);
    }

    @Transactional
    public void trans2() throws InterruptedException {
        d.setPrice(6.6);
//        dishRepo.save(d);
        SpringUtil.getEntityManager().merge(d);
    }

    @Transactional
    public void test2(Dish d) throws InterruptedException {
        d.setId(1999);
        SpringUtil.getEntityManager().persist(d);
        Thread.sleep(1000*10);
    }

    @Transactional
    public void test2_1(Dish d) {
        SpringUtil.getEntityManager().persist(d);
    }
}
