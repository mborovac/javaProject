<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
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
			<a href="/aplikacija2/setcolor?color=white">WHITE</a>
		</p>
		<p>
			<a href="/aplikacija2/setcolor?color=red">RED</a>
		</p>
		<p>
			<a href="/aplikacija2/setcolor?color=green">GREEN</a>
		</p>
		<p>
			<a href="/aplikacija2/setcolor?color=cyan">CYAN</a>
		</p>
	</body>
	
</html>