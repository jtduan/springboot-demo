package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.constants.ResponseType;
import rest.constants.Role;
import rest.entity.User;
import rest.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/insertCooker")
    public ResponseType insertCooker(User u){
        return userService.addEmployee(u, Role.COOKER);
    }

    @RequestMapping("/insertWaitress")
    public ResponseType insertWaitress(User u){
        return userService.addEmployee(u, Role.WAITRESS);
    }

}
