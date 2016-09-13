package rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rest.dao.AccessRepo;
import rest.dao.UserRepo;

/**
 * Created by jtduan on 2016/9/9.
 * 注意：save的时候级联对象不能为“游离态（会触发级联保存或者报错）”
 * Repo.getOne（类似于load()）与findOne(类似于get())区别
 * 在测试环境中，getPrincipal返回的不是User对象
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
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth == null){
//            logger.info("不存在的控制器");
//            AccessHistory history = new AccessHistory(null,request.getRemoteAddr(),request.getRequestURL().toString());
//            accessRepo.save(history);
//            return ;
//        }
//        Object obj =auth.getPrincipal();
//
//        if(obj instanceof User){
//            User user = (User)obj;
//            logger.info("AOP : " + request.getRequestURL().toString()+":"+user.getId());
//            AccessHistory history = new AccessHistory(userRepo.getOne(user.getId()),request.getRemoteAddr(),request.getRequestURL().toString());
//            accessRepo.save(history);
//            return;
//        }
//        else if(obj instanceof UserDetails){
//            logger.info("测试用户："+((UserDetails)obj).getUsername());
//        }
//        else{
//            logger.info("游客："+obj.toString());
//        }
//        AccessHistory history = new AccessHistory(null,request.getRemoteAddr(),request.getRequestURL().toString());
//        accessRepo.save(history);
//        return;
    }

//    @AfterReturning(returning = "ret", pointcut = "method()")
//    public void doAfterReturning(Object ret) throws Throwable {
//    }

}
