<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

	<head></head>

	<body>
		<h1>${poll.title}</h1>
		<p>${poll.message}</p>
		
		<ol>
			<c:forEach var="option" items="${pollOptions}">
			<li><a href="/aplikacija5/servleti/glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>
			</c:forEach>
		</ol>
	</body>
	
</html>