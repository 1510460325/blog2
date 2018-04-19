package cn.wzy.listen;

import cn.wzy.controller.BaseController;
import cn.wzy.dao.Blog_InfoDao;
import cn.wzy.dao.impl.Blog_InfoDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineListen implements HttpSessionListener {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static Blog_InfoDao blog_InfoDao = (Blog_InfoDaoImpl)context.getBean("blog_InfoDaoImpl");

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        synchronized (this){
            if (BaseController.online_num <= 0)
                BaseController.online_num = 0;
            BaseController.online_num++;
            blog_InfoDao.addLookNum();
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        synchronized (this){
            if (BaseController.online_num >= 1)
                BaseController.online_num--;
            else
                BaseController.online_num = 0;
        }
    }
}
