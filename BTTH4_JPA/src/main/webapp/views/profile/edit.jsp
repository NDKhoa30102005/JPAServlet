<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Edit Profile</title>
</head>
<body>
	<h2>Chỉnh sửa Profile</h2>

	<c:if test="${message != null}">
		<p style="color: green">${message}</p>
	</c:if>
	<c:if test="${error != null}">
		<p style="color: red">${error}</p>
	</c:if>

	<form action="${pageContext.request.contextPath}/profile/update"
		method="post" enctype="multipart/form-data">
		Full Name: <input type="text" name="fullname"
			value="${profile.fullname}" /><br /> Phone: <input type="text"
			name="phone" value="${profile.phone}" /><br /> Ảnh: <input
			type="file" name="file" /><br />
		<c:if test="${profile.images != null}">
			<img
				src="${pageContext.request.contextPath}/uploads/profile/${profile.images}"
				width="150" />
		</c:if>
		<br />
		<button type="submit">Cập nhật</button>
	</form>

	<a href="${pageContext.request.contextPath}/profile">Quay lại trang
		profile</a>
</body>
</html>
