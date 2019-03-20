<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dash board</title>
</head>
<body>
	<h3>Dash board View</h3>
	<hr>
	<a href="${pageContext.request.contextPath}/useraccount/changepassword">Change Password</a>
	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	<hr>
	<h4>Current Page Type: ${pageType}</h4>
	<!-- Conditional Check for Page Type Message -->
	<c:choose>
		
		<c:when test="${pageType == 'message'}">
			<h3>Message Table View</h3>
			<table border=1>
				<tr>
					<th>Sent To</th>
					<th>Message Content</th>
					<th>Select</th>
				</tr>
				<c:forEach items="${msgList}" var="msg">
					<c:choose>
						<c:when test="${msg.status = true }">
							<tr>
								<td><strong>${msg.sentTo.name}</strong></td>
								<td><strong>${msg.message}</strong></td>
								<td><input type="checkbox"/></td>
							</tr>
						</c:when>
						<c:otherwise>
						Second Condition
							<tr>
								<td>${msg.sentTo.name}</td>
								<td>${msg.message}</td>
								<td><input type="checkbox"/></td>
							</tr>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</table>


		</c:when>
	</c:choose>


</body>
</html>