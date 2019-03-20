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
<h2>List of Request</h3>
<nav>
	<a href="${contextpath}/manager/dashboard">Dashboard</a>
	<a href="${contextpath}/logout">Logout</a>
</nav>
<hr>

<!-- Displays User Account OR User details list -->
		<c:if test = "${pageType eq 'useraccountview'}">
		
		<table class="tg" style="border: 2px solid black">
			<tr>
				<th width="80">User Id</th>	
				<th width="80">Name</th>
				<th width="80">Role</th>
				<th width="80">Delete</th>
			</tr>
			<c:forEach items="${useraccounts}" var="ua">
				<tr>
					<td>${ua.user_id}</td>
					<td>${ua.name}</td>
					<td>${ua.role}</td>
					<td><a
						href="<c:url value='/manager/useraccount/view/remove/${ua.user_id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
				
		</c:if>
		
		<c:if test = "${pageType eq 'userview'}">
				
		</c:if>

</body>
</html>