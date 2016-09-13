package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jtduan on 2016/9/6.
 * 需求：该MyHttpSessionListener无效（bug）
 * 解决：需要在Controller中使用HttpSession参数才能触发创建session，单纯访问页面不会触发session创建
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, HttpSession> map = new ConcurrentHashMap<String, HttpSession>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        User user = (User) event.getSession().getAttribute("user");
        map.remove(user.getEmail());
        logger.info("用户下线："+user.getEmail());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();

        if (name.equals("user")) {
            User user = (User) event.getValue();
            if (map.get(user.getEmail()) != null) {
                HttpSession session = map.get(user.getEmail());
                session.invalidate();
                logger.warn(user.getEmail() + "被踢下线");
            }
            map.put(user.getEmail(), event.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("attributeReplaced");

    }
}
