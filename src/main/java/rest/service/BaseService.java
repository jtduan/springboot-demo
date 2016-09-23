package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.DishType;
import rest.constants.RandomGenerator;
import rest.constants.SpringUtil;
import rest.dao.DishRepo;
import rest.dao.OrderRepo;
import rest.dao.RestaurantRepo;
import rest.dao.UserRepo;
import rest.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    private RestaurantRepo restaurantRepo;

    /**
     * Todo：使用hibernate batch完成大量数据插入
     */
    public void initDataBase() {

        if(!restaurantRepo.existByName("红烧家餐厅")) {

            Restaurant restaurant = new Restaurant("红烧家餐厅");

            List<Dish> dishes = new ArrayList<Dish>();
            dishes.add(new Dish("红烧排骨", DishType.SMALL, 37, true,restaurant));
            dishes.add(new Dish("红烧牛肉", DishType.STANDARD, 46, true,restaurant));
            dishes.add(new Dish("红烧鸡柳", DishType.BIG, 55, true,restaurant));

            List<Cooker> cookers = new ArrayList<Cooker>();
            cookers.add(new Cooker("厨师1号", "123456",restaurant));
            cookers.add(new Cooker("厨师2号", "123456",restaurant));

            List<Adminer> adminers = new ArrayList<Adminer>();
            adminers.add(new Adminer("admin@qq.com", "123456",restaurant));

            restaurant.getDishes().addAll(dishes);
            restaurant.getCookers().addAll(cookers);
            restaurant.getAdminers().addAll(adminers);

            restaurantRepo.save(restaurant);
        }
        if(!userRepo.existsByEmail("jtduan@qq.com")) {
            User user = new User("jtduan@qq.com", "jtduan", "jtduan");
            userRepo.save(user);
        }
    }

    @Transactional
    public User InsertRandomUser() {
        User user = new User(RandomGenerator.email(), RandomGenerator.text(5), "jtduan");
        return userRepo.save(user);
    }

    @Transactional
    public long getNotExistIdin(Class<?> c) {
        for (int i = 0; i < 100; i++) {
            long id = RandomGenerator.getRandom(100000) + 100000;
            if (SpringUtil.getEntityManager().find(c, id) == null) return id;
        }
        throw new RuntimeException("找不到可用id");
    }
}
