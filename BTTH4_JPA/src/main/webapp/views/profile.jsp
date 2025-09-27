<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

    <h2>Update Profile</h2>
    <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label>Full Name</label>
            <input type="text" name="fullname" class="form-control" value="${fullname}">
        </div>
        <div class="mb-3">
            <label>Phone</label>
            <input type="text" name="phone" class="form-control" value="${phone}">
        </div>
        <div class="mb-3">
            <label>Profile Image</label>
            <input type="file" name="image" class="form-control">
            <c:if test="${not empty image}">
                <img src="${pageContext.request.contextPath}/uploads/${image}" alt="Profile" class="img-thumbnail mt-2" width="150">
            </c:if>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>

    <c:if test="${not empty message}">
        <div class="alert alert-success mt-3">${message}</div>
    </c:if>

</body>
</html>
