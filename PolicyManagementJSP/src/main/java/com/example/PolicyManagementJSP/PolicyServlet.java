package com.example.PolicyManagementJSP;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/policy")
public class PolicyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/policydb",
                "admin",
                "password"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    List<Policy> policies = getPolicies();
                    request.setAttribute("policies", policies);
                    request.getRequestDispatcher("/WEB-INF/views/policyList.jsp").forward(request, response);
                    break;
                case "view":
                    String policyId = request.getParameter("id");
                    Policy policy = getPolicy(policyId);
                    request.setAttribute("policy", policy);
                    request.getRequestDispatcher("/WEB-INF/views/policyView.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("/policy?action=list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }

    private List<Policy> getPolicies() throws SQLException {
        List<Policy> policies = new ArrayList<>();
        String query = "SELECT * FROM policies";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Policy policy = new Policy();
                policy.setId(rs.getInt("id"));
                policy.setPolicyNumber(rs.getString("policy_number"));
                policy.setCustomerName(rs.getString("customer_name"));
                policy.setStartDate(rs.getDate("start_date"));
                policy.setEndDate(rs.getDate("end_date"));
                policies.add(policy);
            }
        }
        return policies;
    }

    private Policy getPolicy(String id) throws SQLException {
        Policy policy = new Policy();
        String query = "SELECT * FROM policies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    policy.setId(rs.getInt("id"));
                    policy.setPolicyNumber(rs.getString("policy_number"));
                    policy.setCustomerName(rs.getString("customer_name"));
                    policy.setStartDate(rs.getDate("start_date"));
                    policy.setEndDate(rs.getDate("end_date"));
                }
            }
        }
        return policy;
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
