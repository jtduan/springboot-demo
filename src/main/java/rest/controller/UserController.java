package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rest.constants.SpringUtil;
import rest.constants.VIP;
import rest.dao.EntityDao;
import rest.dao.UserRepo;
import rest.entity.User;

/**
 * Created by jtduan on 2016/9/5.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepo userRepo;

    @Value("${constants.priviliage.write}")
    private String text;

    @ResponseBody
    @RequestMapping("get")
    public String get(){
        SpringUtil.getBean(EntityDao.class).save();
        return "success";
    }

    @ResponseBody
    @RequestMapping("insert")
    public String insert(){
        User user = new User();
        user.setEmail("djtqq.com");
        user.setName("asd");
        user.setVip(VIP.VIP0);
        user.setPwd("djt");
        userRepo.save(user);
        return "success";
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(){
        User user=userRepo.findByEmail("jtduan@qq.com");
        if(user!=null) {
            user.setEmail("djt@qq.com");
            userRepo.save(user);
            return "success";
        }
        return "fail";
    }

    @RequestMapping(value = "new", method=RequestMethod.POST)
    public String addUser(@Validated User user, BindingResult validation) {
        if (validation.hasErrors()) {
           for(String str:validation.getModel().keySet()){
               System.out.println(validation.getModel().get(str));
           }
           return "form";
        }
        user.setVip(VIP.VIP0);
        User saved = userRepo.save(user);
        return "form";
    }

//    @RequestMapping("/login")
//    public String login(@RequestParam String email,@RequestParam String pwd,HttpSession session){
//        User user=userRepo.login(email,pwd);
//        if(user!=null){
//            session.setAttribute("user",user);
//            return "home";
//        }
//        return "index";
//    }
}
