
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body>
	<c:choose>
		<c:when test="${status == true }">
			<h2>Successfully Logged In</h2>
			<c:redirect url="homepage.jsp"></c:redirect>
		</c:when>
		<c:when test="${status == false }">
			<h2 style="color: Red">Unsuccessful Login. Please Try Again.</h2>
		</c:when>
		
	</c:choose>
	<form action="/api/sm/add" method="post">
		Text:<input type="text" name="messageInput" value="Dummy Data Entry" /> <br /> <br />
		<br /> <input type="submit" value="submit" />"
	</form>
	<h4>Clicky Here To Register</h4>
	<form action="${pageContext.request.contextPath}/useraccount/register" method="post">
		Username:<input type="text" name="username" value="Lorem Ipsum" /> <br /> <br />
		Password:<input type="text" name="password" value="Lorem Ipsum" /> <br /> <br />
		<br /> <input type="submit" value="submit" />"
	</form>
</body>
</html>