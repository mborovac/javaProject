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
		<p>
			Illegal parameter values!
		</p>
		<p>
			Allowed values: </br>
			a & b: [-100, 100] </br>
			n: [1, 5]
		</p>
	</body>
	
</html>