
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<body>

	<h1>Welcome To The HomePage</h1>

	<c:choose>
	<c:when test = "${status == true }">
	<h2>Successfully Logged In</h2>
	</c:when>
	<c:when test = "${status == false }">
	<h2 style="color:Red"> Unsuccessful Login. Please Try Again.</h2>
	<jsp:include page="login.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
	<h3> Simply Redirect to login Page?
	</h3></c:otherwise>
	</c:choose>
	
</body>
</html>