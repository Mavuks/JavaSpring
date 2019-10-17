package exservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/orders/form")
@MultipartConfig
public class OrderFormServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello");


    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String string = req.getParameter("orderNumber");
//        ObjectMapper obj = new ObjectMapper();
//
//        Order order = new Order();
//        order.setOrderNumber(string);
//
//        String value = obj.writeValueAsString(order);
//        Long id = order.getId();
//        Order.setSaveMap(id,value);
//
//        resp.setHeader("Content-Type", "text/plain");
//        resp.getWriter().print(id);









    }
}
