package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.Constant;
import rest.constants.ResponseType;
import rest.constants.SpringUtil;
import rest.dao.CookerRepo;
import rest.dao.UserRepo;
import rest.entity.Cooker;
import rest.entity.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * //        Hibernate.initialize(user.getUserType());
 */
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired  //数据库服务类
    private UserRepo userRepo;

    @Autowired  //数据库服务类
    private CookerRepo cookerRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        String[] arr = name.split("\\|");
        if (!check(arr))
            throw new UsernameNotFoundException("找不到对应的用户");
        if (arr[1].equals("cooker")) {
            Optional<Cooker> cooker = cookerRepo.findById(Long.parseLong(arr[0]));
            return new SecurityCooker(cooker.orElseThrow(() -> {
                return new UsernameNotFoundException("CookerId " + arr[0] + " not found");
            }));
        } else {
            Optional<User> user = userRepo.findByEmail(arr[0]);
            return new SecurityUser(user.orElseThrow(() -> {
                return new UsernameNotFoundException("UserEmail " + arr[0] + " not found");
            }));

        }
    }

    private boolean check(String[] arr) {
        if (arr[1].equals("cooker") && arr[0].matches("[0-9]+")) return true;
        if (arr[1].equals("user") && arr[0].matches(Constant.emailPattern)) return true;
        return false;
    }

    @Transactional
    public ResponseType updateUser(long id, String type, String value) {
        if (!(SpringUtil.checkUser(id) || SpringUtil.checkAdmin())) {
            return ResponseType.PERMISSION_DENIED;
        }
        try {
            User u = userRepo.getOne(id);
            switch (type) {
                case "name":
                    u.setName(value);
                    break;
                case "email":
                    u.setEmail(value);
                    break;
                case "pwd":
                    u.setPwd(value);
                    break;
                default:
                    return ResponseType.USER_FILED_FINAL;
            }
            userRepo.save(u);
            userRepo.flush(); //否则会在return之后提交事务，获取不正确的返回值
            return ResponseType.SUCCESS;
        } catch (EntityNotFoundException e) {
            return ResponseType.USER_NOTFOUND;
        } catch (ConstraintViolationException e) {
            return ResponseType.INVALID_EMAIL;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseType delete(Long id) {
        try {
            userRepo.delete(id);
            return ResponseType.SUCCESS;
        } catch (EmptyResultDataAccessException e) {
            return ResponseType.USER_NOTFOUND;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    public User findbyEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("UserName " + email + " not found");
        });
    }

    /**
     * 普通用户注册(消费者)
     *
     * @param user
     * @return
     */
    public ResponseType register(User user) {
        if (user.getEmail() == null || user.getPwd() == null || !user.getEmail().matches(Constant.emailPattern)) {
            return ResponseType.INPUT_ERROR;
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseType.USER_NAME_CONFLICT;
        }
        if (user.getName() == null) {
            user.setName(user.getEmail());
        }
        userRepo.save(new User(user));
        return ResponseType.SUCCESS;
    }

    /**
     * 厨师注册
     *
     * @param user
     * @return
     */
    public ResponseType addCooker(Cooker cooker) {
        cookerRepo.save(cooker);
        return ResponseType.SUCCESS;
    }
}
