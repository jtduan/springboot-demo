package rest.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rest.dao.UserRepo;

import javax.annotation.PostConstruct;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by hero on 2016/9/5.
 */
@Deprecated
public enum Role {

    ADMIN("ADMIN") {
        public String get() {
            return userRepo.findByEmail("jtduan@qq.com").getName();
        }
    },
    WAITRESS("WAITRESS") {
        public String get() {
            return "WRITE";
        }
    },
    COOKER("COOKER"){
        public String get(){ return "COOKER";}
    };

    private String name;

    private static UserRepo userRepo;

    Role(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }

    public String get() {
        throw new AbstractMethodError();
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}

@Component
class PriviliageInjector {
    @Autowired
    private UserRepo userRepo;

    @PostConstruct
    public void postConstruct() {
//        for (Priviliage rt : EnumSet.allOf(Priviliage.class))
        Role.ADMIN.setUserRepo(userRepo);
    }
}
