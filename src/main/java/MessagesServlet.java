import com.mysql.cj.jdbc.Driver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

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
            ResultSet rs = s.executeQuery("SELECT * FROM messages");

            while (rs.next()) {
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (Error | SQLException e) {
            e.printStackTrace();
        }

    }
}
