<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Category</title>
</head>
<body>
    <h2>Add New Category</h2>

    <!-- Form to add category -->
    <form action="${pageContext.request.contextPath}/admin-category/create" method="post" enctype="multipart/form-data">
        <p>
            <label>Category Code:</label><br>
            <input type="text" name="categoryCode" required>
        </p>
        <p>
            <label>Category Name:</label><br>
            <input type="text" name="categoryName" required>
        </p>
        <p>
            <label>Category Image:</label><br>
            <input type="file" name="images">
        </p>
        <p>
            <button type="submit">Add Category</button>
        </p>
    </form>
</body>
</html>
