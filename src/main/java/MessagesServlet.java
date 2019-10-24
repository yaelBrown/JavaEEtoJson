import com.google.gson.Gson;
import com.mysql.cj.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "MessagesServlet", urlPatterns = "/messages")
public class MessagesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hello Post");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Someone wanted to see the messages !");

        try {
            DriverManager.registerDriver(new Driver());
            Connection c = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUsername(),
                    Config.getPassword()
            );

            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM msg");

            Map<String, String> gsonReturn = new HashMap<>();

            Gson gson = new Gson();

            while (rs.next()) {
                gsonReturn.put(rs.getString(2), rs.getString(3));
            }

            String output = gson.toJson(gsonReturn);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(output);
            out.flush();

        } catch (Error | SQLException e) {
            e.printStackTrace();
        }

    }
}
