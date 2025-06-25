package com.example.PolicyManagementJSP;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/legacy")
public class LegacyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://ep-damp-king-a8sbvr2i-pooler.eastus2.azure.neon.tech/policydb";
    private static final String DB_USER = "Policy_owner";
    private static final String DB_PASSWORD = "npg_CEZQltdPx27M";
   
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
            );
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("generateReport".equals(action)) {
            generateReport(request, response);
        } else {
            showLegacyPage(request, response);
        }
    }

    private void showLegacyPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Legacy System</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Legacy Policy Management</h1>");
        out.println("<form action='/legacy?action=generateReport' method='post'>");
        out.println("<input type='text' name='startDate' placeholder='Start Date (YYYY-MM-DD)'>");
        out.println("<input type='text' name='endDate' placeholder='End Date (YYYY-MM-DD)'>");
        out.println("<input type='submit' value='Generate Report'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (startDate == null || endDate == null) {
            response.sendRedirect("/legacy");
            return;
        }

        try {
            String query = "SELECT * FROM policies WHERE start_date BETWEEN ? AND ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Policy Report</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Policy Report</h1>");
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Policy Number</th>");
            out.println("<th>Customer Name</th>");
            out.println("</tr>");
            
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("policy_number") + "</td>");
                out.println("<td>" + rs.getString("customer_name") + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error generating report", e);
        }
    }

    @Override
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
