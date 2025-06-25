<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Policy Management</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { border-collapse: collapse; width: 100%; }
        th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        tr:nth-child(even) { background-color: #f9f9f9; }
    </style>
</head>
<body>
    <h1>Policy Management System</h1>
    
    <div style="margin: 20px 0;">
        <form action="/policy" method="get">
            <input type="hidden" name="action" value="view">
            <input type="text" name="id" placeholder="Enter policy ID">
            <button type="submit">View Policy</button>
        </form>
    </div>

    <table>
        <tr>
            <th>ID</th>
            <th>Policy Number</th>
            <th>Customer Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="policy" items="${policies}">
            <tr>
                <td>${policy.id}</td>
                <td>${policy.policyNumber}</td>
                <td>${policy.customerName}</td>
                <td>${policy.startDate}</td>
                <td>${policy.endDate}</td>
                <td>
                    <a href="/policy?action=view&id=${policy.id}">View</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <script>
        // This is a legacy script that mixes JavaScript with JSP
        function confirmDelete(id) {
            if (confirm('Are you sure you want to delete this policy?')) {
                window.location.href = '/policy?action=delete&id=' + id;
            }
        }
    </script>
</body>
</html>
