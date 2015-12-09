<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.concurrent.TimeUnit" %>

<%
String color = "white";
if(session.getAttribute("pickedBgCol") != null) {
	color = session.getAttribute("pickedBgCol").toString();
}
long timeDifference = System.currentTimeMillis() - Long.parseLong(application.getAttribute("creationTime").toString());
int miliseconds = (int) timeDifference % 1000;
int seconds = (int) (timeDifference / 1000) % 60 ;
int minutes = (int) ((timeDifference / (1000*60)) % 60);
int hours   = (int) ((timeDifference / (1000*60*60)) % 24);
long days = TimeUnit.MILLISECONDS.toDays(timeDifference);
%>

<html>

	<head></head>
	
	<body bgcolor="<%= color %>">
		
		Time since start: <%= days %> days <%= hours %> hours <%= minutes %> minutes <%= seconds %> seconds <%= miliseconds %> miliseconds!
		
	</body>
	
</html>