import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String fn = request.getParameter("name");
        String id = request.getParameter("email");
        String pwd = request.getParameter("pass");

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practical", "root", "");
            PreparedStatement ps = con.prepareStatement("INSERT INTO employee VALUES (?, ?, ?)");

            ps.setString(1, fn);
            ps.setString(2, id);
            ps.setString(3, pwd);

            int x = ps.executeUpdate();
            if (x > 0) {
                response.sendRedirect("Login.html");
            } else {
                out.println("Registration unsuccessful");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Registration Servlet";
    }
}