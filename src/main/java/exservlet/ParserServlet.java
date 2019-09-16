package exservlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/parser")
public class ParserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ParserMapper mapper = new ParserMapper();

        String string =  Util.readStream(req.getInputStream());


        String proov = mapper.parse(string);

        resp.setHeader("Content-Type", "application/json");
//        System.out.println(proov);
        resp.getWriter().print(proov);









    }
}
