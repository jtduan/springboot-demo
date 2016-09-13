package rest.backthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jtduan on 2016/9/6.
 * 需求：使用线程池，没有任务时挂起，等待被唤醒
 * 2、同一菜品，大中小份一起做
 */
@Component("ChieferTask")
//@Scope("prototype")
public class Chiefer implements Runnable {

    private WaitedDish dish;

    final static Logger logger = LoggerFactory.getLogger(Chiefer.class);

    @Override
    public void run() {
        CustomQueue.t = Thread.currentThread();
        while (true) {
            logger.info("chief线程睡眠中.....");
            LockSupport.park(); //只能针对两个线程的通信，多个Chiefer时不能使用park()方法
            while (true) {
                dish = CustomQueue.poll();
                if (dish != null) {
                    for (int i = 0; i < 5 + dish.num; i++) {
                        try {
                            logger.info("chief正在烹饪...");
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.info("完成菜品："+dish.dish.getName());
                } else {
                    break;
                }
            }
        }
    }
}