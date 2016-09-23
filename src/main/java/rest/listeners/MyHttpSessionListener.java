package rest.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.constants.CurrentUserUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jtduan on 2016/9/6.
 * Todo：该MyHttpSessionListener无效（fixed）
 * 解决：需要在Controller中使用HttpSession参数才能触发创建session，单纯访问页面不会触发session创建
 *
 * 两个new Long(12345) hashcode是相等的
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
        Long user = (Long) event.getSession().getAttribute(CurrentUserUtils.INSTANCE.CUR_USER);
        if (user == null) return;
        map.remove(user);
        logger.info("用户下线：" + user);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();

        if (name.equals(CurrentUserUtils.INSTANCE.CUR_USER)) {
            String user = (String) event.getValue();
            if (map.get(user) != null) {
                HttpSession session = map.get(user);
                /**
                 * 通知被踢下线
                 */
                session.invalidate();
                logger.warn(user + "被踢下线");
            }
            map.put(user, event.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
