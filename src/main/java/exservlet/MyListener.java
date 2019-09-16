package exservlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class MyListener implements ServletContextListener{


    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(33333333);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }


}
