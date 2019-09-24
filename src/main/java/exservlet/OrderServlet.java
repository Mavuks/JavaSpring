package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import exservlet.model.Order;

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


        String id = request.getParameter("id");

        Long id1 = Long.parseLong(id);
        String value = Order.getSaveMap(id1);

        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(value);

        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String string =  Util.readStream(req.getInputStream());

        ObjectMapper asd = new ObjectMapper();

        Order order = asd.readValue(string, Order.class);

        String value = asd.writeValueAsString(order);

        Order.setSaveMap(order.getId(),value);

        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(value);









    }
}
