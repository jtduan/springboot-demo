package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.*;
import rest.dao.ConsumerRepo;
import rest.dao.DishRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;
import rest.entity.Employee;
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

    @Autowired
    private ConsumerRepo consumerRepo;

    /**
     * 需求：使用hibernate batch完成大量数据插入
     */
    public void initDataBase(){
        User user = new User("jtduan@qq.com","jtduan","jtduan", VIP.VIP0);
        if(userRepo.findByEmail("jtduan@qq.com")==null){
            user.getUserType().recharge(12888);
            userRepo.save(user);
        }

        User user2 = new User("jtduan2@qq.com","jtduan","jtduan2", VIP.VIP1);
        if(userRepo.findByEmail("jtduan2@qq.com")==null){
            user.getUserType().recharge(12888);
            userRepo.save(user2);
        }

        this.InsertRandomCooker();
        this.InsertRandomWaitress();

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
        return userRepo.save(user);
    }

    @Transactional
    public User InsertRandomCooker(){
        User user = new User(RandomGenerator.email(),RandomGenerator.text(5),"jtduan", Role.COOKER);
        return userRepo.save(user);
    }

    @Transactional
    public User InsertRandomWaitress(){
        User user = new User(RandomGenerator.email(),RandomGenerator.text(5),"jtduan", Role.WAITRESS);
        return userRepo.save(user);
    }

    @Transactional
    public User InsertRandomAdmin(){
        User user = new User(RandomGenerator.email(),RandomGenerator.text(5),"jtduan", Role.ADMIN);
        return userRepo.save(user);
    }

    @Transactional
    public long getNotExistIdin(Class<?> c){
        for(int i=0;i<100;i++){
            long id = RandomGenerator.getRandom(100000)+100000;
            if(SpringUtil.getEntityManager().find(c,id)==null) return id;
        }
        throw new RuntimeException("找不到可用id");
    }

    @Transactional
    public long getExistIdinUser(){
        List<User> l = userRepo.findAll();
        return l.get(RandomGenerator.getRandom(l.size()-1)).getId();
    }

    @Transactional
    public long getExistIdinDish() {
        List<Dish> l = dishRepo.findAll();
        return l.get(RandomGenerator.getRandom(l.size()-1)).getId();
    }

    @Transactional
    public User getTestUser() {
        return userRepo.findByEmail("jtduan@qq.com");
    }

    @Transactional
    public long getTestUserId() {
        return userRepo.findByEmail("jtduan@qq.com").getId();
    }

    @Transactional
    public User getTestUser2() {
        return userRepo.findByEmail("jtduan2@qq.com");
    }

    @Transactional
    public long getTestUser2Id() {
        return userRepo.findByEmail("jtduan2@qq.com").getId();
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
        consumerRepo.addOrCutRemain(1,18);
    }
}
