
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme1/css/main.css" type="text/css"></link>
<title>Login Page</title>
</head>
<body>
	<h1>Welcome Client</h1>

	<!-- Check if user is already logged in -->
	
	<c:if test="${not empty user}">
		Hi ${user.username}
	</c:if>
	
	<!-- CASE WHEN USER LOGGED IN & REQUEST PASSWORD CHANGE -->
	<c:if test="${not empty user && pagetype eq 'changepassword' }">
			<h4>Please change Username and Password </h4>
			<form
				action="${pageContext.request.contextPath}/useraccount/changepassword"method="post">
				Your Id Is: <input type="text" name="user_id" value="${user.user_id}"readonly 		/> <br>    
				Please Enter New Username:	<input type="text" name="username" value="_Enter Here_" /> <br>
				Please Enter New Password:	<input type="text" name="password" value="_Enter Here_" /> <br>
				<input type="submit" value="submit" />
			</form>
			<a href="${pageContext.request.contextPath}/useraccount/register">Register</a>
		</c:if>

	<!-- If user not logged in. We check if
			1) REQUEST IS TO CHANGE PASSWORD
			2) CHECK IF REGISTRATION PAGE TO BE DISPLAYED
			3) DISPLAY NORMAL LOGIN		
	 -->
	<c:if test="${empty user}">
					<!-- CHECK IF USER REGISTRATION TO BE DISPLAYED -->
		<c:if test="${pagetype eq 'register' }">
			<h4>Click Here To Register</h4>
			<form
				action="${pageContext.request.contextPath}/useraccount/register"
				method="post">
				Username:<input type="text" name="username" value="username" /> <br />
				Password:<input type="text" name="password" value="password" /> <br />
				RoleType:  
				<select name="roletype">
					<c:forEach var="role" items="${roles}">
						<option value="${role.value}" ${role.value eq 'REGULAR' ? 'select' : '' }>${role.value}
						</option>
					</c:forEach>
				</select>
				<br /> <br /> <br /> <input type="submit" value="submit" />     <br /> <br />
				<a href="${pageContext.request.contextPath}/login">Return to Login Page</a>
			</form>
		</c:if>					
				<!--  IF LOGIN, CHECK IF CASE FOR SUCCESSFULL OR INVALID -->				
						<!-- IF STATUS = FALSE i.e. INVALID LOGIN -->
							<!-- STANDARD LOGIN -->
		<c:if test="${pagetype eq 'login' }">
			<c:choose>
				<c:when test="${status == 'success' }">
					<h2>Successfully Logged In</h2>
					<c:redirect url="homepage.jsp"></c:redirect>
				</c:when>
					
					
				<c:when test="${status == 'invalid'}">
					<h2 style="color: Red">Unsuccessful Login. Please Try Again.</h2>
				</c:when>
				
				<c:when test="${status == 'success_register'}">
					<em>Account registration successful. Please login:</em>
				</c:when>
					
				<c:otherwise>
					<h3>Login</h3>
					
				</c:otherwise>
			</c:choose>
			<form action="${pageContext.request.contextPath}/login"	method="post">
				Please Enter New Username:	<input type="text" name="username" value="_Enter Here_" /> <br>
				Please Enter New Password:	<input type="text" name="password" value="_Enter Here_" /> <br> 
				<input type="submit" value="submit" />
				<a href="${pageContext.request.contextPath}/useraccount/register">Register</a>
			</form>
		</c:if>
	</c:if>
	<c:if test="${not empty user}">
	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	</c:if>
			
	

</body>
</html>