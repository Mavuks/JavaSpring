package main;


import conf.MvcConfig;
import conf.PostgresDataSource;
import dao.OrderDao;
import model.Order;
import model.Orderrows;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        MvcConfig.class, PostgresDataSource.class);

        try (ctx) {

            OrderDao dao = ctx.getBean(OrderDao.class);

            ArrayList<Orderrows> asd = new ArrayList<>();

            Orderrows row = new Orderrows("CPU", 2, 2);
            Orderrows row1 = new Orderrows("GPU", 2, 200);

            asd.add(row);
            asd.add(row1);
            System.out.println(row.toString());


            Order order = new Order("A458", asd);
            Order order1 = new Order("A457", asd);

            dao.insertOrder(order);
            System.out.println(dao.insertOrder(order1));



        }


    }
}