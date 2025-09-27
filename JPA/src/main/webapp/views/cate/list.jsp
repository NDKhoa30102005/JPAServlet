<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category List</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4 text-center">Category Management</h2>

        <!-- Add button -->
        <div class="mb-3 text-end">
            <a href="${pageContext.request.contextPath}/admin-category/create" class="btn btn-success">
                <i class="fas fa-plus"></i> Add New
            </a>
        </div>

        <!-- Category Table -->
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>Category Code</th>
                    <th>Category Name</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categorys}">
                    <tr>
                        <td>${category.categoryCode}</td>
                        <td>${category.categoryName}</td>
                        <td>
                            <img src="${pageContext.request.contextPath}/images/category/${category.images}" 
                                 alt="Category Image" class="img-thumbnail" width="100">
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin-category/edit?categoryId=${category.categoryId}" 
                               class="btn btn-warning btn-sm">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/admin-category/delete?categoryId=${category.categoryId}" 
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure you want to delete this category?');">
                                <i class="fas fa-trash"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
