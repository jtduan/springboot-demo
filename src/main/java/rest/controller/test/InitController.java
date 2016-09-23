package rest.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.dao.RestaurantRepo;
import rest.dao.UserRepo;
import rest.entity.User;

/**
 * @author jtduan
 * @date 2016/9/23
 */
@Controller
@RequestMapping("init")
public class InitController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    @RequestMapping("")
    @ResponseBody
    public String init() {
        User u = userRepo.getOne(1l);
        u.setName("段锦涛");
        userRepo.save(u);
        return "SUCCESS";
    }
}
