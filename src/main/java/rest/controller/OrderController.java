package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.constants.ResponseType;
import rest.dao.DishRepo;
import rest.entity.Consumer;
import rest.entity.Dish;
import rest.entity.User;
import rest.module.websocket.Notification;
import rest.module.websocket.NotificationService;
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
    private UserService userService;
    @Autowired
    private DishRepo dishRepo;
    @Autowired
    private OrderService orderervice;

    @RequestMapping(value="",method = RequestMethod.GET)
    public String order(Model model,HttpSession session){
        User u =(User)session.getAttribute("user");
        List<Dish> list=dishRepo.findAll();
        model.addAttribute("dishes",list);
        model.addAttribute("orders",orderervice.findNotFinishedOrders(u));
        model.addAttribute("remain",userService.getRemain(u));
        return "dishes/desk";
    }

    @RequestMapping(value={"{id:[0-9]+}"},produces = { "text/x-responseType" },method = RequestMethod.POST)
    @ResponseBody
    public ResponseType order(@PathVariable long id,HttpSession session){
        User u =(User)session.getAttribute("user");
        return orderervice.order(u,id);
    }

    @RequestMapping(value={"/press/{id:[0-9]+}"},produces = { "text/x-responseType" },method = RequestMethod.POST)
    @ResponseBody
    public ResponseType press(@PathVariable long id,HttpSession session){
        return orderervice.press(id);
    }

    @RequestMapping(value={"/cancel/{id:[0-9]+}"},produces = { "text/x-responseType" },method = RequestMethod.POST)
    @ResponseBody
    public ResponseType cancel(@PathVariable long id,HttpSession session){
        return orderervice.cancel(id);
    }

}
