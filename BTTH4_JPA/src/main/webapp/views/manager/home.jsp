<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f1f5f9;
        }
        .dashboard {
            max-width: 600px;
            margin: 80px auto;
        }
        .card {
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <div class="card p-4 text-center">
            <h2 class="mb-3 text-primary">Welcome Manager</h2>
            <p class="text-muted">This is the manager dashboard.</p>
            <a href="${pageContext.request.contextPath}/admin-category" class="btn btn-primary mt-3">Manage Categories</a>
			<a href="${pageContext.request.contextPath}/profile" class="btn btn-success">View Profile</a>
            
        </div>
    </div>
</body>
</html>
