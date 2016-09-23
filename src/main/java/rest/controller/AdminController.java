package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.constants.ResponseType;
import rest.constants.Role;
import rest.entity.User;
import rest.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
}
