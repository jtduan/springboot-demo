package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.Constant;
import rest.constants.ResponseType;
import rest.constants.Role;
import rest.constants.VIP;
import rest.dao.UserRepo;
import rest.entity.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired  //数据库服务类
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + email + " not found");
        }
        return new SecurityUser(user);
    }

    @Transactional
    public ResponseType updateUser(long id, String type, String value) {
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
                case "vip":
                    u.setVip(VIP.valueOf(value));
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
        }catch (EmptyResultDataAccessException e){
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

    public ResponseType register(User user) {
        if(user.getEmail()==null || user.getPwd() == null || !user.getEmail().matches(Constant.emailPattern)){
            return ResponseType.INPUT_ERROR;
        }
        User validUser = userRepo.findByEmail(user.getEmail());
        if(validUser != null) {
            return ResponseType.USER_NAME_CONFLICT;
        }
        user.setVip(VIP.VIP1);
        user.setRoles(EnumSet.of(Role.USER));
        if(user.getName()==null){
            user.setName(user.getEmail());
        }
        userRepo.save(new User(user));
        return ResponseType.SUCCESS;
    }

    private class SecurityUser extends User implements UserDetails {

        public SecurityUser(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.ADMIN.getName());
            authorities.add(authority);
            return authorities;
        }

        @Override
        public String getPassword() {
            return this.getPwd();
        }

        @Override
        public String getUsername() {
            return this.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
