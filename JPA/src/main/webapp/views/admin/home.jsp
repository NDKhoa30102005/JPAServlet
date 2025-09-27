<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .dashboard {
            max-width: 600px;
            margin: 80px auto;
        }
        .card {
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        h2 {
            color: #0d6efd;
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <div class="card p-4 text-center">
            <h2>Welcome Admin</h2>
            <p class="text-muted">This is the admin dashboard.</p>
            <a href="${pageContext.request.contextPath}/admin-category" class="btn btn-primary">Manage Categories</a>
        </div>
    </div>
</body>
</html>
