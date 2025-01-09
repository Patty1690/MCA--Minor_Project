import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class Forget extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form parameters
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");

        try (PrintWriter out = response.getWriter()) {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practical", "root", "")) {
                
                // Prepare SQL query to insert data into the 'patient' table
                String query = "UPDATE employee SET pass = ? WHERE email = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                  ps.setString(1, pass); 
            ps.setString(2, email);
            int result = ps.executeUpdate();

            if (result > 0) {
                response.sendRedirect("Login.html");
            } else {
                System.out.println("Error resetting password!");
            }

                    // Execute the query
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        out.println("Registration successful!");
                    } else {
                        out.println("Registration failed.");
                    }
                }
            } catch (SQLException e) {
                out.println("Database error: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
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
        return "Servlet to handle user registration";
    }
}