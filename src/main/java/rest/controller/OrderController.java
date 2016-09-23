package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.constants.CurrentUserUtils;
import rest.constants.Debug;
import rest.constants.ResponseType;
import rest.dao.DishRepo;
import rest.entity.Dish;
import rest.entity.Restaurant;
import rest.service.OrderService;
import rest.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * order()id参数为dishId
 * press()id参数为orderId
 */
@Controller
@RequestMapping("/order")
@PreAuthorize("authenticated")
public class OrderController {
    @Autowired
    private DishRepo dishRepo;
    @Autowired
    private OrderService orderervice;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String order(Model model, HttpSession session) {
        List<Dish> list = dishRepo.findByRestaurant(new Restaurant(1));
        model.addAttribute("dishes", list);
        return "dishes/desk";
    }

    @Debug("order service")
    @RequestMapping(value = {"{id:[0-9]+}"}, produces = {"text/x-responseType"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseType order(@PathVariable long id, HttpSession session) {
        String user = (String) session.getAttribute(CurrentUserUtils.INSTANCE.CUR_USER);
        return orderervice.order(user, new long[]{id});
    }

    @RequestMapping(value = {"/press/{id:[0-9]+}"}, produces = {"text/x-responseType"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseType press(@PathVariable long id, HttpSession session) {
        return ResponseType.SUCCESS;
    }

    @RequestMapping(value = {"/cancel/{id:[0-9]+}"}, produces = {"text/x-responseType"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseType cancel(@PathVariable long id, HttpSession session) {
        return orderervice.cancel(id);
    }

}
