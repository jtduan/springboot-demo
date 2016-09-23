package rest.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rest.constants.Constant;
import rest.entity.Cooker;

import java.util.Collection;
import java.util.Collections;

public class SecurityCooker extends Cooker implements UserDetails {

    public SecurityCooker(Cooker cooker) {
        super(cooker);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if(this.getUserType() instanceof Employee){
//            for (Role role : ((Employee)this.getUserType()).getRoles()) {
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
//                authorities.add(authority);
//            }
//            return authorities;
//        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public String getUsername() {
        return Constant.CookerPrefix+String.valueOf(this.getId());
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
