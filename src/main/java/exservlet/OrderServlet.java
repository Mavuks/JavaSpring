package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import model.Order;
import model.ValidationError;
import model.ValidationErrors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    private AnnotationConfigApplicationContext annotationConfigApplicationContext;


    @Override
    public void init() {
        ServletContext context = getServletContext();

        this.annotationConfigApplicationContext = (AnnotationConfigApplicationContext) context.getAttribute("context");
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        Order order = null;


        OrderDao dao = annotationConfigApplicationContext.getBean(OrderDao.class);


        if (request.getParameterMap().containsKey("id")) {
            String id = request.getParameter("id");
            int idValue = Integer.parseInt(id);


            order = dao.findOrdersById(idValue);


            response.getWriter().print(order);
            response.setHeader("Content-Type", "application/json");//NOPMD
        } else {


            response.getWriter().print(dao.findOrders());
            response.setHeader("Content-Type", "application/json");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String string = Util.readStream(req.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(string, Order.class);

        OrderDao dao = annotationConfigApplicationContext.getBean(OrderDao.class);


        Order orderValue = dao.insertOrder(order);


        if (order.getOrderNumber().length() < 2) {
            ValidationErrors validationErrors = new ValidationErrors();
            ValidationError validationError = new ValidationError();
            List<ValidationError> list = new ArrayList<>();

            validationError.setCode("too_short_orderNumber");
            list.add(validationError);
            validationErrors.setErrors(list);


            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(mapper.writeValueAsString(validationErrors));
            resp.setStatus(400);
        } else if (order.getOrderRows() == null) {

            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(orderValue);

        } else {

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
      //  Long idValue = Integer.valueOf(id);
        Long idValue = Long.valueOf(id);
        OrderDao dao = annotationConfigApplicationContext.getBean(OrderDao.class);

        dao.deleteRowById(idValue);


    }
}
