package main;


import config.Config;
import config.PostgresDataSource;
import dao.OrderDao;
import model.Order;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(Config.class, PostgresDataSource.class);


        try (ctx) {
            System.out.println("tere");
            System.out.println(ctx);
            OrderDao dao = ctx.getBean(OrderDao.class);

            Order order = dao.insertOrder(new Order("A123"));

            System.out.println(order);

            System.out.println(dao.findOrders());
        }

    }
}