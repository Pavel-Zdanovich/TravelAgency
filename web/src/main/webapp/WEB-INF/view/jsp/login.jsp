<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Login page</title>
</head>
<body>
	<div th:if="${param.error}">
		Invalid username and password.
	</div>
	<div th:if="${param.logout}">
		You have been logged out.
	</div>
	<form th:action="@{/login_action}" method="post">
		<div>
			<label> User Name : <input type="text" name="username"/></label>
		</div>
		<div>
			<label> Password: <input type="password" name="password"/> </label>
		</div>
		<div>
			<input type="submit" value="Sign In"/>
		</div>
	</form>
</body>
</html>