package rest.service;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rest.constants.Role;
import rest.entity.Consumer;
import rest.entity.Employee;
import rest.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

public class SecurityUser extends User implements UserDetails {

    public SecurityUser(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if(this.getType() instanceof Consumer){
////            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
////            authorities.add(authority);
////            return authorities;
//            return Collections.EMPTY_LIST;
//        }
        if(this.getType() instanceof Employee){
            for (Role role : ((Employee)this.getType()).getRoles()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
            return authorities;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.getId());
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
