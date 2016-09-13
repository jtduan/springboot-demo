package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import rest.backthreads.Chiefer;
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

        SpringUtil.getBean(BaseService.class).initDataBase();
        new Thread(new Chiefer()).start();
    }
}