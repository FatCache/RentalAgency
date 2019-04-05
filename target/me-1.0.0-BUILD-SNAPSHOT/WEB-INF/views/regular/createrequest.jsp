<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Request</title>
</head>
<body>
<span>Hi User: ${user.username}</span>
<h3>Create Parking Request</h3>
<nav>
<a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<hr>
	<form:form method="POST" modelAttribute="parkingrequest"
		action="${pageContext.request.contextPath}/user/request/create/">
		<table>
			<tr>
				<td>User ID:</td>
				<td><form:input path="user_id" value="${sessionScope.user.user_id}" 
				readonly="true" type="text"/></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><form:textarea path="description" rows="3" cols="20" /></td>
			</tr>

			<tr>
				<td>Start Time:</td>
				<td><form:input type="date" path="startTime" /></td>
			</tr>
			<tr>
				<td>Select Row Spot</td>
				<td><form:select path="rsp">
						<form:options items="${rowspot}" itemValue="name" itemLabel="name" />
					</form:select></td>
			</tr>
			<tr>
				<td>Select Column Spot</td>
				<td><form:select path="csp">
						<form:options items="${colSpot}" itemValue="name" itemLabel="name" />
					</form:select></td>
			</tr>
			<td colspan="2"><input type="submit" value="Save Changes" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>