package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import exservlet.dao.OrderDao;
import exservlet.model.Order;
import util.DataSourceProvider;
import util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/api/orders")
public class    OrderServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        if(request.getParameterMap().containsKey("id")) {
            String id = request.getParameter("id");
            Long id1 = Long.parseLong(id);
            String value = Order.getSaveMap(id1);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(value);
        }else{
            OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

            response.setHeader("Content-Type", "application/json");

            response.getWriter().print(dao.findOrders());

        }






        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String string =  Util.readStream(req.getInputStream());
       // System.out.println(string);

        ObjectMapper asd = new ObjectMapper();

        Order order = asd.readValue(string, Order.class);

        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

        dao.insertOrder(order);


        String value = asd.writeValueAsString(order);

        Order.setSaveMap(order.getId(),value);

        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(value);









    }
}
