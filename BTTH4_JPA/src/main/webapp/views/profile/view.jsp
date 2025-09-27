<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Profile</title>
</head>
<body>
	<h2>Thông tin cá nhân</h2>

	<p>Full Name: ${profile.fullname}</p>
	<p>Phone: ${profile.phone}</p>
	<c:if test="${profile.images != null}">
		<img
			src="${pageContext.request.contextPath}/uploads/profile/${profile.images}"
			width="150" />

	</c:if>

	<a href="${pageContext.request.contextPath}/profile/edit">Chỉnh sửa
		profile</a>
	<a href="${pageContext.request.contextPath}/profile"
		class="btn btn-success">View Profile</a>

</body>
</html>
