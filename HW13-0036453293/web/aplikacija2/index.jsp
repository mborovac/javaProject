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
			<a href="colors.jsp">Background color chooser</a>
		</p>
		<p>
			<a href="/aplikacija2/squares?a=100&b=120">Table of squares</a>
		</p>
		<p>
			<a href="/aplikacija2/stories/funny.jsp">Funny short story in random color</a>
		</p>
		<p>
			<a href="/aplikacija2/powers?a=1&b=100&n=3">Powers</a>
		</p>
		<p>
			<a href="/aplikacija2/appinfo.jsp">Application run time</a>
		</p>
	</body>
	
</html>