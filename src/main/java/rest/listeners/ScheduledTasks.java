package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by jtduan on 2016/9/6.
 * Todo：使用Quartz实现多线程Tasks并能动态开启和暂停定时任务
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        ConcurrentTaskScheduler t;
//        log.info("The time is now {}", dateFormat.format(new Date()));
    }

//    @Bean
//	public ConcurrentTaskScheduler taskScheduler() {
//		ConcurrentTaskScheduler task = new ConcurrentTaskScheduler();
//		task.scheduleAtFixedRate(new SockTaskRunner(), 1000*60*SocketSessionInfo.SESSION_INVALID);
//		return task;
//	}
}
