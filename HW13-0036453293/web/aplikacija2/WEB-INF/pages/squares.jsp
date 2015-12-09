<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String color = "white";
if(session.getAttribute("pickedBgCol") != null) {
	color = session.getAttribute("pickedBgCol").toString();
}
%>

<html>

	<head></head>
	
	<body bgcolor="<%= color %>">
		<table border="1">
			<c:forEach var="r" items="${result}">
		  		<tr><td>${r.number}</td><td>${r.square}</td></tr>
		 	</c:forEach>
		</table>
	</body>
	
</html>