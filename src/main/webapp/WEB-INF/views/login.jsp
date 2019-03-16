
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body>

<!-- Only for Password Change-->
<c:if test = "${pagetype eq 'passwordchange'}">
<h4>Please change Username & Password</h4>
<form action="${pageContext.request.contextPath}/useraccount/changepassword" method="post">
		Your Id Is: <input type = "text" name="user_id" value ="${user_id}" readonly/> </br>
				Please Enter New Username:<input type="text" name="username" value="_Enter Here_" /> <br /> <br />
		Please Enter New Password:<input type="text" name="password" value="_Enter Here_" /> <br /> <br />
		<br /> <input type="submit" value="submit" />"
	</form>

</c:if>

<!-- Defaults to regular login -->
<c:if test = "${empty pagetype || pagetype eq 'login' }">

	<c:choose>
		<c:when test="${status == true }">
			<h2>Successfully Logged In</h2>
			<c:redirect url="homepage.jsp"></c:redirect>
		</c:when>
		<c:when test="${status == false }">
			<h2 style="color: Red">Unsuccessful Login. Please Try Again.</h2>
		</c:when>
		
	</c:choose>
	<!-- 
	<form action="/api/sm/add" method="post">
		Text:<input type="text" name="messageInput" value="Dummy Data Entry" /> <br /> <br />
		<br /> <input type="submit" value="submit" />
	</form>
	 -->
	<h4>Click Here To Register</h4>
	<form action="${pageContext.request.contextPath}/useraccount/register" method="post">
		Username:<input type="text" name="username" value="Lorem Ipsum" /> <br /> <br />
		Password:<input type="text" name="password" value="Password Ipsum" /> <br /> <br />
		<br /> <input type="submit" value="submit" />"\
	</form>
	</c:if>
</body>
</html>