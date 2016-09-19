package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.config.ViewHelper;
import rest.dao.DishRepo;
import rest.entity.Dish;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jtduan on 2016/9/8.
 * Todo：开发自定义视图自动添加对应页面的权限，前端根据属性判断展示不同内容
 */
@Controller
@RequestMapping("/dishes")
@PreAuthorize("authenticated")
public class DishController {

    private static final Logger logger = LoggerFactory.getLogger(DishController.class);

    @Autowired
    private DishRepo dishRepo;

    /**
     * dish列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpSession session, Model model) {
        List<Dish> list = dishRepo.findAll();
        model.addAttribute("dishes", list);
        return "dishes/list";
    }

    /**
     * 添加dish
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(@Validated Dish dish, BindingResult validation) {
        if (validation.hasErrors()) {
            return "dishes/detail";
        }
        dishRepo.save(dish);
        return "redirect:";
    }

    /**
     * 更新dish
     *
     * @param dish
     * @return
     */
    @RequestMapping(value = "{id:[0-9]+}", method = RequestMethod.PUT)
    @ResponseBody
    public String update(Dish dish) {
        return ViewHelper.renderJson("0", "success1");
    }

    /**
     * 删除dish
     *
     * @return
     */
    @RequestMapping(value = "/test/{id:ddd}", method = RequestMethod.GET)
    @ResponseBody
    public String del() {
        System.out.println("sssssss");
        return ViewHelper.renderJson("0", "success2");
    }

    /**
     * dish详情页,添加dish页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[0-9-]+}", method = RequestMethod.GET)
    public String getDish(@PathVariable long id, Model model) {
        if (id == -1) {
            model.addAttribute("dishes", new Dish());
            return "dishes/detail";
        }
        Dish dish = dishRepo.findOne(id);
        if (dish == null) {
            return "no_resource";
        }
        model.addAttribute("dishes", dish);
        return "dishes/detail";
    }
}
