<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"></c:set>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${contextpath}/resources/theme1/css/main.css" type="text/css"></link>
<title>Create Request</title>
</head>
<body>
<span>Hi User: ${user.username}</span>

<nav>
<a href="${pageContext.request.contextPath}/regular/dashboard">Dashboard</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>