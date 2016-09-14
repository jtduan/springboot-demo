package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.*;
import rest.dao.DishRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.User;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by jtduan on 2016/9/6.
 */
@Service("initService")
public class BaseService {
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

    @Transactional
    public User InsertRandomUser(){
        User user = new User(RandomGenerator.email(),RandomGenerator.text(5),"jtduan", VIP.values()[RandomGenerator.getRandom(4)]);
        user.getFund().setRemain(RandomGenerator.getRandom(300));
        return userRepo.save(user);
    }

    @Transactional
    public User InsertRandomAdmin(){
        User user = new User(RandomGenerator.email(),RandomGenerator.text(5),"jtduan", VIP.values()[RandomGenerator.getRandom(4)]);
        user.getFund().setRemain(RandomGenerator.getRandom(300));
        user.setRoles(EnumSet.of(Role.ADMIN));
        return userRepo.save(user);
    }

    @Transactional
    public long getNotExistIdinUser(){
        for(int i=0;i<100;i++){
            long id = RandomGenerator.getRandom(100000)+100000;
            if(SpringUtil.getEntityManager().find(User.class,id)==null) return id;
        }
        throw new RuntimeException("找不到可用id");
    }

    @Transactional
    public long getExistIdinUser(){
        List<User> l = userRepo.findAll();
        return l.get(RandomGenerator.getRandom(l.size()-1)).getId();
    }
}
