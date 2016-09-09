package rest.schedule;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by jtduan on 2016/9/6.
 * 需求：该MyHttpSessionListener无效（bug）
 * 解决：需要在Controller中使用HttpSession参数才能触发创建session，单纯访问页面不会触发session创建
 */
//@WebListener
public class MyHttpSessionListener implements HttpSessionListener,HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session 被销毁");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("attributeAdded");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

        System.out.println("attributeRemoved");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("attributeReplaced");

    }
}
