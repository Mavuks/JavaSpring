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
public class OrderServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        Order order = null;

        if (request.getParameterMap().containsKey("id")) {
            String id = request.getParameter("id");
            Long id1 = Long.parseLong(id);
            DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
            OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

            order = dao.findOrdersById(id1);

            response.setHeader("Content-Type", "application/json");//NOPMD
            response.getWriter().print(order);

        } else {
            OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

            response.setHeader("Content-Type", "application/json");

            response.getWriter().print(dao.findOrders());


        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = Util.readStream(req.getInputStream());

        ObjectMapper asd = new ObjectMapper();
        Order order = asd.readValue(string, Order.class);
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

        Order asdf = dao.insertOrder(order);

        if (order.getOrderRows() == null) {

            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(asdf);

        } else {
            for (int i = 0; i < order.getOrderRows().size(); i++) {

                String itemName = order.getOrderRows().get(i).getItemName();
                int quantity = order.getOrderRows().get(i).getQuantity();
                Long price = order.getOrderRows().get(i).getPrice();

                dao.insertOrderRow(itemName, quantity, price, asdf.getId());

            }

        }

        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(asdf);


    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String id = req.getParameter("id");
        Long id1 = Long.parseLong(id);

        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

        dao.deleteRowById(id1);


    }
}
