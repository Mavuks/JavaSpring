package main;


import conf.HsqlDataSource;
import conf.MvcConfig;
import dao.OrderDao;
import model.Order;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        MvcConfig.class, HsqlDataSource.class);

        try (ctx) {

            OrderDao dao = ctx.getBean(OrderDao.class);


            Order order = new Order("A456", null);

            dao.insertOrder(order);




        }




    }
}