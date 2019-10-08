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
        response.setHeader("Content-Type", "application/json");
        if (request.getParameterMap().containsKey("id")) {
            String id = request.getParameter("id");
            Long idValue = Long.parseLong(id);
            DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
            OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

            order = dao.findOrdersById(idValue);


            response.getWriter().print(order);

        } else {
            OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());


            response.getWriter().print(dao.findOrders());

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = Util.readStream(req.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(string, Order.class);
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

        Order orderValue = dao.insertOrder(order);

        if (order.getOrderRows() == null) {

            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(orderValue);


        } else {


            for (int i = 0; i < order.getOrderRows().size(); i++) {

                String itemName = order.getOrderRows().get(i).getItemName();
                int quantity = order.getOrderRows().get(i).getQuantity();
                Long price = order.getOrderRows().get(i).getPrice();

                dao.insertOrderRow(itemName, quantity, price, orderValue.getId());

            }
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(orderValue);
        }


    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String id = req.getParameter("id");
        Long idValue = Long.parseLong(id);

        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        OrderDao dao = new OrderDao(DataSourceProvider.getDataSource());

        dao.deleteRowById(idValue);


    }
}
