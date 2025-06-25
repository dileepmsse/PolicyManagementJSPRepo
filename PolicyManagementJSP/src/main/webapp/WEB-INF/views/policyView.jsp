<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Policy Details</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .policy-card { padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .policy-card h2 { margin-top: 0; color: #333; }
        .policy-info { margin: 10px 0; }
        .policy-info label { font-weight: bold; }
    </style>
</head>
<body>
    <h1>Policy Details</h1>
    
    <div class="policy-card">
        <h2>Policy Information</h2>
        
        <div class="policy-info">
            <label>ID:</label>
            <span>${policy.id}</span>
        </div>
        
        <div class="policy-info">
            <label>Policy Number:</label>
            <span>${policy.policyNumber}</span>
        </div>
        
        <div class="policy-info">
            <label>Customer Name:</label>
            <span>${policy.customerName}</span>
        </div>
        
        <div class="policy-info">
            <label>Start Date:</label>
            <span>${policy.startDate}</span>
        </div>
        
        <div class="policy-info">
            <label>End Date:</label>
            <span>${policy.endDate}</span>
        </div>
    </div>

    <div style="margin-top: 20px;">
        <a href="/policy?action=list">Back to Policy List</a>
    </div>

    <%-- Legacy script that demonstrates inline JavaScript --%>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var policyNumber = '${policy.policyNumber}';
            if (policyNumber) {
                document.title = 'Policy ' + policyNumber + ' - Details';
            }
        });
    </script>
</body>
</html>
