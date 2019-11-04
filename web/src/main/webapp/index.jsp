<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		
		<h1>Index JSP!</h2>
		<h2>Message : ${message}</h2>
		<form action="/login" method="GET">
		    <input type = "submit" value = "Go!"/>
		</form>
		
	</body>
</html>
