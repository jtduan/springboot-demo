package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.config.ViewHelper;
import rest.dao.DishRepo;
import rest.dao.UserRepo;
import rest.entity.Dish;

/**
 * Created by jtduan on 2016/9/8.
 */
@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DishRepo dishRepo;

    public String order(Dish dish, int num){
        return ViewHelper.renderJson("0000","点餐成功");
    }

}
