package rest.backthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.controller.UserController;
import rest.entity.Dish;
import rest.entity.User;

import java.util.Map;
import java.util.Queue;

/**
 * Created by jtduan on 2016/9/6.
 */
public class Waitress {
    private Map<Dish,Queue<User>> map;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    public void notifyUser(User user,Dish dish){
        log.info("通知："+user.getEmail()+"-"+dish.getName()+dish.getUserType());
    }

    public void updateDish(Dish waited_dish){
//        Queue<User> users = map.get(waited_dish);
//        for(int i=0;i<waited_dish.num;i++) {
//            notifyUser(users.poll(),waited_dish.dishes);
//        }
    }

    public void add(){

    }

    public void remove(){

    }
}
