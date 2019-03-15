<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simple Message</title>
</head>
<body>

	
	<c:if test="${!empty simplemessage}">

		<form action="${contextpath}/api/sm/add/" method="post">
			Message ID: <input text="text" value="${simplemessage.message_id}"
				readonly /> Text:<input type="text" name="descr"
				value="${simplemessage.content}" /> <br /> <br /> <br /> <input
				type="submit" value="submit" />
		</form>
	</c:if>
	<c:if test="${empty simplemessage}">
		<form action="${contextpath}/api/sm/add/" method="post">

			Text:<input type="text" name="descr" value="Ming" /> <br /> <br />
			<br /> <input type="submit" value="submit" />"
		</form>

	</c:if>

	<c:if test="${!empty smlist}">
		<table class="tg">
			<tr>
				<th width="80">Message ID</th>
				<th width="220">Message Description</th>
			</tr>
			<c:forEach items="${smlist}" var="sm">
				<tr>
					<td>${sm.message_id}</td>
					<td>${sm.content}</td>
					<td>${sm.status}</td>
					<td><a href="<c:url value='/api/sm/edit/${sm.message_id}' />">Edit</a></td>
					<td><a
						href="<c:url value='/api/sm/remove/${sm.message_id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>