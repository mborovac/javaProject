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
	
		<h>
			OS usage
		</h>
		<p>
			Here are the results of OS usage in survey that we completed.
		</p>
		<img src="/aplikacija2/reportImage" alt="OS usage image">
	</body>
	
</html>