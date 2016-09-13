package rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rest.dao.AccessRepo;
import rest.dao.UserRepo;
import rest.entity.AccessHistory;
import rest.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jtduan on 2016/9/9.
 * 注意：save的时候级联对象不能为“游离态（会触发级联保存或者报错）”
 * Repo.getOne（类似于load()）与findOne(类似于get())区别
 */
@Aspect
@Component
public class AccessLog {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccessRepo accessRepo;

    @Autowired
    private UserRepo userRepo;

    @Pointcut("execution(public * rest.controller..*.*(..))")
    public void method(){}

    @Before("method()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        logger.info("AOP : " + request.getRequestURL().toString()+":"+user.getId());
        AccessHistory history = new AccessHistory(userRepo.getOne(user.getId()),request.getRemoteAddr(),request.getRequestURL().toString());
        accessRepo.save(history);
    }

//    @AfterReturning(returning = "ret", pointcut = "method()")
//    public void doAfterReturning(Object ret) throws Throwable {
//    }

}
