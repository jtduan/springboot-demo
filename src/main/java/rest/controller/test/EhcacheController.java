package rest.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.dao.DishRepo;
import rest.entity.Dish;

import java.util.List;

/**
 * @author jtduan
 * @date 2016/9/22
 */
@Controller
@RequestMapping("/test/ehcache")
public class EhcacheController {

    @Autowired
    private DishRepo dishRepo;

    @RequestMapping("")
    @ResponseBody
    public List<Dish> listDishes(){
        return dishRepo.findAll();
    }

    @RequestMapping("{id}")
    @ResponseBody
    public Dish listDishes(@PathVariable long id){
        return dishRepo.findOne(id);
    }

}
