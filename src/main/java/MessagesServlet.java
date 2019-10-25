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

        Messages m = new Messages(
                request.getParameter("author"),
                request.getParameter("message")
        );

        try {
            DriverManager.registerDriver(new Driver());
            Connection c = DriverManager.getConnection(
                    Config.getUrl(),
                    Config.getUsername(),
                    Config.getPassword()
            );

            Statement stmt = c.createStatement();

            String sql = String.format("INSERT INTO msg (author, message) VALUES('%s', '%s')", m.getAuthor(), m.getMessage());

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);


        } catch (SQLException e) {
            e.printStackTrace();
        }

            PrintWriter out = response.getWriter();
            out.print("Message saved!");


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
            String sql = "SELECT * FROM msg";

            ResultSet rs = s.executeQuery(sql);

            Map<Integer, String> gsonReturn = new HashMap<>();

            Gson gson = new Gson();

            int count = 0;

            while (rs.next()) {
                System.out.println("rs.getString(3) = " + rs.getString(3));
                gsonReturn.put(count, rs.getString(2) + ": " + rs.getString(3));
                count++;
            }

            System.out.println(gsonReturn.toString());

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
