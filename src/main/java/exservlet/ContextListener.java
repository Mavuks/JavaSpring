package exservlet;

import config.Config;
import config.PostgresDataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextListener implements ServletContextListener{



    @Override
    public void contextInitialized(ServletContextEvent sce) {

        var ctx = new AnnotationConfigApplicationContext(Config.class, PostgresDataSource.class);
      //  System.out.println(ctx);
        ServletContext context = sce.getServletContext();

        context.setAttribute("context", ctx);
      //  System.out.println(context);

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
   //     DataSourceProvider.closePool();
    }


}
