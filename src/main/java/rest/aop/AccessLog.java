package rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rest.constants.CurrentUserUtils;
import rest.dao.AccessRepo;
import rest.dao.UserRepo;
import rest.entity.AccessHistory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jtduan on 2016/9/9.
 * 注意：save的时候级联对象不能为“游离态（会触发级联保存或者报错）”
 * Repo.getOne（类似于load()）与findOne(类似于get())区别
 * 在测试环境中，getPrincipal返回的不是User对象
 *
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

    /**
     * 提供给SpringAdmin进行监控
     */
    @Autowired
    private CounterService counterService;

    @Autowired
    private GaugeService gaugeService;

    @Before("method()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        counterService.increment(joinPoint.getSignature() + "");

//        Long id = CurrentUserUtils.INSTANCE.getUserId();
//        String url = CurrentUserUtils.INSTANCE.getRequest().getRequestURL().toString();
//        logger.info("用户"+"~"+id+"~"+url);

//        AccessHistory history = null;
//        if(id == null){
//            history = new AccessHistory(null,CurrentUserUtils.INSTANCE.getRequest().getRemoteAddr(),
//                    CurrentUserUtils.INSTANCE.getRequest().getRequestURL().toString());
//        }
//        else{
//            history = new AccessHistory(userRepo.getOne(id),CurrentUserUtils.INSTANCE.getRequest().getRemoteAddr(),
//                    CurrentUserUtils.INSTANCE.getRequest().getRequestURL().toString());
//        }
//        accessRepo.save(history);
//        return;
    }

    @Around("execution(* rest.controller.*.*(..))")
    public Object latencyService(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result =pjp.proceed();
        long end = System.currentTimeMillis();
        gaugeService.submit(pjp.getSignature().toString(), end - start);
        return result;
    }
}
