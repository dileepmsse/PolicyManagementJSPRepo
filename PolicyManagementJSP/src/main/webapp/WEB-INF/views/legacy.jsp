<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
<head>
    <title>Legacy System</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .legacy-header { background-color: #f0f0f0; padding: 10px; }
        .legacy-form { margin: 20px; }
        .legacy-table { border-collapse: collapse; width: 100%; }
        .legacy-table th, .legacy-table td { border: 1px solid #ddd; padding: 8px; }
    </style>
</head>
<body>
    <div class="legacy-header">
        <h1>Legacy Policy Management</h1>
        <p>System Version: 1.0.0</p>
    </div>

    <div class="legacy-form">
        <form action="/legacy?action=generateReport" method="post">
            <div>
                <label>Start Date:</label>
                <input type="text" name="startDate" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
            </div>
            <div>
                <label>End Date:</label>
                <input type="text" name="endDate" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
            </div>
            <button type="submit">Generate Report</button>
        </form>
    </div>

    <%-- Legacy scriptlet that demonstrates business logic in JSP --%>
    <%
    if (request.getParameter("action") != null && "generateReport".equals(request.getParameter("action"))) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/policydb",
                "admin",
                "password"
            );

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            String query = "SELECT * FROM policies WHERE start_date BETWEEN ? AND ?";
            stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            rs = stmt.executeQuery();

            out.println("<table class='legacy-table'>");
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

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    %>

    <script>
        // Legacy JavaScript that demonstrates inline script
        function validateDates() {
            var startDate = document.getElementById('startDate').value;
            var endDate = document.getElementById('endDate').value;
            
            if (startDate > endDate) {
                alert('Start date cannot be after end date!');
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
