<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Regular View</title>
</head>
<body>
<h2>Dashboard: Regular</h2>

<span>Hi User: ${user.username}</span>

<nav>
<a href="${pageContext.request.contextPath}/regular/dashboard">Dashboard</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<hr>
Action
<nav>
<a href="${pageContext.request.contextPath}/user/request/create">Create Parking Request</a>
<a href="#">View My Requests</a>
</nav>




</body>
</html>