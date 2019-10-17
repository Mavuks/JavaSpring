package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import model.Order;
import model.ValidationError;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
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

        ServletContext context = getServletContext();
        AnnotationConfigApplicationContext configApplicationContext = (AnnotationConfigApplicationContext) context.getAttribute("context");


        OrderDao dao = configApplicationContext.getBean(OrderDao.class);


        if (request.getParameterMap().containsKey("id")) {
            String id = request.getParameter("id");
            int idValue = Integer.parseInt(id);


            order = dao.findOrdersById(idValue);


            response.getWriter().print(order);

        } else {


            response.getWriter().print(dao.findOrders());

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = Util.readStream(req.getInputStream());
        ObjectMapper mapper = new ObjectMapper();

        Order order = mapper.readValue(string, Order.class);
        ServletContext context = getServletContext();
        AnnotationConfigApplicationContext configApplicationContext = (AnnotationConfigApplicationContext) context.getAttribute("context");


        OrderDao dao = configApplicationContext.getBean(OrderDao.class);

        Order orderValue = dao.insertOrder(order);
        if(order.getOrderNumber().length() < 2){

            ValidationError validationError = new ValidationError();
            validationError.setCode("too_short_number");
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(validationError);
            System.out.println(validationError);
            resp.setStatus(400);
        }

        else if (order.getOrderRows() == null) {

            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(orderValue);
            System.out.println("siin1");

        } else {

            System.out.println("siin2");
            for (int i = 0; i < order.getOrderRows().size(); i++) {

                String itemName = order.getOrderRows().get(i).getItemName();
                int quantity = order.getOrderRows().get(i).getQuantity();
                Integer price = order.getOrderRows().get(i).getPrice();

                dao.insertOrderRow(itemName, quantity, price, orderValue.getId());

            }
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(orderValue);
        }


    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String id = req.getParameter("id");
        Integer idValue = Integer.valueOf(id);
        ServletContext context = getServletContext();
        AnnotationConfigApplicationContext configApplicationContext = (AnnotationConfigApplicationContext) context.getAttribute("context");


        OrderDao dao = configApplicationContext.getBean(OrderDao.class);
        dao.deleteRowById(idValue);


    }
}
