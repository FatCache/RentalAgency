<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manager View</title>
</head>
<body>
<h2>Dashboard: Manager</h2>

<span>Welcome Manager: ${user.username}</span>

<nav>
<a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<hr>
Action
<nav>
<a href="${pageContext.request.contextPath}/manager/request/parkingrequest/view">View Request</a>
<a href="${pageContext.request.contextPath}/manager/useraccount/view">View User Accounts</a>
<a href="${pageContext.request.contextPath}/manager/user/view">View Users</a>

</nav>

</body>
</html>