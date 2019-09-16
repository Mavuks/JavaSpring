package exservlet;


import exservlet.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/parser")
public class parserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderMapper mapper = new OrderMapper();

        String string =  Util.readStream(req.getInputStream());
        Order order = mapper.parse(string);
        String proov = mapper.stringify(order);

        resp.setHeader("Content-Type", "application/json");

        resp.getWriter().print(proov);









    }
}
