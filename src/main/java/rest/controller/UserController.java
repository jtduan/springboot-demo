package rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.constants.ResponseType;
import rest.entity.User;
import rest.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jtduan on 2016/9/5.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> list() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET)
    @PreAuthorize("authenticated")
    public User getUser(@PathVariable Long id, HttpSession session) {
        if (((User) session.getAttribute("user")).getId() != id) {
            return null;
        }
        return userService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = {"text/plain"})
    public ResponseType registerUser(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.PUT, produces = {"text/plain"})
    @PreAuthorize("authenticated")
    public ResponseType updateUser(@PathVariable long id, @RequestParam String type, @RequestParam String value, HttpSession session) {
        return userService.updateUser(id, type, value);
    }

    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.DELETE, produces = {"text/plain"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseType deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}
