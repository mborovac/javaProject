<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Login</title>
		
		<style type="text/css">
		.greska {
			font-family: fantasy;
			font-weight: bold;
			font-size: 0.9em;
			color: #FF0000;
		}
		</style>
	</head>
	
	<body>	
		<form action="login" method="post">
		
		Username <input type="text" name="username" size="15"><br>
		
		Password <input type="password" name="password" size="30"><br>
		
		<c:if test="${greska != null}">
			<div class="greska"><c:out value="${greska}"></c:out> </div>
		</c:if>
		
		<input type="submit" name="metoda" value="Login">
		<input type="submit" name="metoda" value="Odustani">
		</form>
		
	</body>
</html>
