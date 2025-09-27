<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #4e73df, #1cc88a);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            border-radius: 20px;
            box-shadow: 0 5px 25px rgba(0, 0, 0, 0.2);
            padding: 40px;
            background: #fff;
            width: 100%;
            max-width: 400px;
        }
        .login-card h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: bold;
            color: #4e73df;
        }
        .btn-login {
            width: 100%;
            border-radius: 50px;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <h2>Welcome Back</h2>

        <!-- Login form -->
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required placeholder="Enter your username">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required placeholder="Enter your password">
            </div>
            <button type="submit" class="btn btn-primary btn-login">Login</button>
        </form>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger mt-3 text-center">
                ${errorMessage}
            </div>
        </c:if>
    </div>
</body>
</html>
