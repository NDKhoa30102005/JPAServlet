<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1cc88a, #36b9cc);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .dashboard {
            max-width: 500px;
            width: 100%;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        h2 {
            color: #1cc88a;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <div class="card p-4 text-center">
            <h2>Welcome User</h2>
            <p class="text-muted">This is your personal dashboard.</p>
            <a href="${pageContext.request.contextPath}/admin-category" class="btn btn-success">User Categories</a>
			<a href="${pageContext.request.contextPath}/profile" class="btn btn-success">View Profile</a>
            
        </div>
    </div>
</body>
</html>
