package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.constants.DishType;
import rest.constants.Role;
import rest.constants.VIP;
import rest.dao.DishRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.User;

/**
 * Created by jtduan on 2016/9/6.
 */
@Service("initService")
public class AllSevice {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DishRepo dishRepo;

    /**
     * 需求：使用hibernate batch完成大量数据插入
     */
    public void initDataBase(){
        User user = new User("jtduan@qq.com","jtduan","jtduan", VIP.VIP0);
        if(userRepo.findByEmail("jtduan@qq.com")==null){
            user.getFund().setCost(128);
            userRepo.save(user);
        }

        Dish dish = new Dish("红烧排骨", DishType.SMALL,37,true);
        if(dishRepo.findByNameAndType("红烧排骨",DishType.SMALL)==null)dishRepo.save(dish);

        dish = new Dish("红烧排骨", DishType.STANDARD,46,true);
        if(dishRepo.findByNameAndType("红烧排骨",DishType.STANDARD)==null)dishRepo.save(dish);

        dish = new Dish("红烧排骨", DishType.BIG,55,true);
        if(dishRepo.findByNameAndType("红烧排骨",DishType.BIG)==null)dishRepo.save(dish);
    }
}
