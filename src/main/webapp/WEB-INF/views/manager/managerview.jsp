<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${contextpath}/resources/theme1/css/main.css" type="text/css"></link>
<title>Request View</title>
</head>
<body>
<h3>Request View</h3>
<nav>
	<a href="${contextpath}/manager/dashboard">Dashboard</a>
	<a href="${contextpath}/logout">Logout</a>
</nav>


<c:if test="${!empty rList}">
<h3>All Request in the System</h3>
		<table class="tg" style="border: 2px solid black">
			<tr>
				<th width="80">Request ID</th>
				<th width="80">User ID</th>
				<th width="220">Message Description</th>
				<th width="80">Start Time</th>
				<th width="80">Row Spot</th>
				<th width="80">Column Spot</th>
				<th width="80">Delete</th>
			</tr>
			<c:forEach items="${rList}" var="r">
				<tr>
					<td>${r.request_id}</td>
					<td>${r.user_id}</td>
					<td>${r.description}</td>
					<td>${r.startTime}</td>
					<td>${r.rsp}</td>
					<td>${r.csp}</td>
					<td><a
						href="<c:url value='/manager/request/parkingrequest/remove/${r.request_id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>