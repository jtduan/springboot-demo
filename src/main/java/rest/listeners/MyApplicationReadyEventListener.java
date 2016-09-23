package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import rest.backthreads.Chiefer;
import rest.backthreads.ChieferDemon;
import rest.constants.SpringUtil;
import rest.service.BaseService;

/**
 * spring boot 启动监听类
 *
 * @author liaokailin
 * @version $Id: MyApplicationStartedEventListener.java, v 0.1 2015年9月2日 下午11:06:04 liaokailin Exp $
 */
public class MyApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(MyApplicationReadyEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("==Springboot启动监听器==");
        logger.info("\n\n" + "=========================================================\n"
                + "Using cache manager: " + SpringUtil.getCacheManager().getClass().getName() + "\n"
                + "=========================================================\n\n");
        SpringUtil.getBean(BaseService.class).initDataBase();
        new Thread(SpringUtil.getBean(ChieferDemon.class)).start();
    }
}