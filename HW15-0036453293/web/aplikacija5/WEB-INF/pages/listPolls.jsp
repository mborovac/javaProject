<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

	<head></head>
	
	<body>
		<h1>Ponuđene ankete</h1>
		<p>Ovo su raspoložive ankete:</p>
		<ol>
			<c:forEach var="poll" items="${polls}">
				<li><a href="/aplikacija5/servleti/glasanje?pollID=${poll.id}">${poll.title} ${poll.message}</a></li>
			</c:forEach>
		</ol>
	</body>
	
</html>