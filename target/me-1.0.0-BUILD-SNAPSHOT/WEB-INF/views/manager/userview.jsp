<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Request</title>
</head>
<body>
<h2>User Management View</h3>
<nav>
	<a href="${contextpath}/manager/dashboard">Dashboard</a>
	<a href="${contextpath}/logout">Logout</a>
</nav>
<hr>

<!-- Displays User Account OR User details list -->
		<c:if test = "${pageType eq 'useraccountview'}">
		<h4>User Account List</h4>
		<table class="tg">
			<tr>
				<th width="80">User Id</th>	
				<th width="80">Username</th>
				<th width="80">Password</th>
			</tr>
			<c:forEach items="${useraccounts}" var="ua">
				<tr>
					<td>${ua.user_id}</td>
					<td>${ua.username}</td>
					<td>${ua.password}</td>
					<td><a
						href="<c:url value='/manager/useraccount/view/remove/${ua.user_id}?viewtype=useraccountview' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
				
		</c:if>
		
		<c:if test = "${pageType eq 'userview'}">
		<h4>User List</h4>
		<table class="tg">
			<tr>
				<th width="80">User Id</th>	
				<th width="80">Name</th>
				<th width="80">Role</th>
			</tr>
			<c:forEach items="${users}" var="ua">
				<tr>
					<td>${ua.user_id}</td>
					<td>${ua.name}</td>
					<td>${ua.role}</td>
			</c:forEach>
		</table>
				
		</c:if>

</body>
</html>