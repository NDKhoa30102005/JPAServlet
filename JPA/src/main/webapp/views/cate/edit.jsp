<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Category</title>
</head>
<body>
    <h2>Edit Category</h2>

    <!-- Hiển thị lỗi nếu có -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <!-- Form chỉnh sửa -->
    <form action="${pageContext.request.contextPath}/admin-category/update" method="post" enctype="multipart/form-data">
        <input type="hidden" name="categoryId" value="${category.categoryId}">

        <p>
            <label>Category Code:</label><br>
            <input type="text" name="categoryCode"
                   value="${category.categoryCode}" required
                   <c:if test="${not empty error}">readonly</c:if>>
        </p>

        <p>
            <label>Category Name:</label><br>
            <input type="text" name="categoryName"
                   value="${category.categoryName}" required
                   <c:if test="${not empty error}">readonly</c:if>>
        </p>

        <p>
            <label>Category Image:</label><br>
            <input type="file" name="images"
                   <c:if test="${not empty error}">disabled</c:if>>
        </p>

        <c:if test="${not empty category.images}">
            <p>
                <img src="${pageContext.request.contextPath}/images/category/${category.images}" 
                     alt="Current Image" width="120">
            </p>
        </c:if>

        <!-- Nút Update -->
        <c:if test="${empty error}">
            <p><button type="submit">Update</button></p>
        </c:if>
    </form>
</body>
</html>
