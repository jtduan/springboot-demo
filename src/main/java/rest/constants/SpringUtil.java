package rest.constants;

/**
 * Created by jtduan on 2016/9/6.
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static EntityManager getEntityManager() {
        return getApplicationContext().getBean(EntityManager.class);
    }

    /**
     * 获取登录用户
     * 通过session.getAttribute("user")获取不会发生异常
     *
     * @return
     */
    @Deprecated
    public static UserDetails getLoginedUser() {
        try {
            Object ud = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return (UserDetails)ud;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断登录用户是否与指定用户一致
     *
     * @param id
     * @return
     */
    public static boolean checkUser(long id) {
        UserDetails ud = getLoginedUser();
        if(ud!=null && String.valueOf(id).equals(ud.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 判断登录用户是超级管理员
     *
     * @return
     */
    public static boolean checkAdmin() {
        UserDetails ud =getLoginedUser();
        if(ud.getAuthorities().contains("ADMIN")) {
            return true;
        }
        return false;
    }
}