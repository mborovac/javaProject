<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
		
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
		<form action="register" method="post">
		
		First name <input type="text" name="firstName" value="${firstName}" size="30"><br>
		<c:if test="${firstNameError != null}">
			<div class="greska"><c:out value="${firstNameError}"></c:out> </div>
		</c:if>
		
		Last name <input type="text" name="lastName" value="${lastName}" size="30"><br>
		<c:if test="${lastNameError != null}">
			<div class="greska"><c:out value="${lastNameError}"></c:out> </div>
		</c:if>
		
		E-mail <input type="text" name="email" value="${email}" size="30"><br>
		<c:if test="${emailError != null}">
			<div class="greska"><c:out value="${emailError}"></c:out> </div>
		</c:if>
		
		Nick name <input type="text" name="nickName" value="${nickName}" size="30"><br>
		<c:if test="${nickNameError != null}">
			<div class="greska"><c:out value="${nickNameError}"></c:out> </div>
		</c:if>
		
		Password <input type="password" name="password" size="30"><br>
		<c:if test="${passwordError != null}">
			<div class="greska"><c:out value="${passwordError}"></c:out> </div>
		</c:if>
		
		<input type="submit" name="metoda" value="Register">
		<input type="submit" name="metoda" value="Cancel">
		</form>
		
	</body>
</html>