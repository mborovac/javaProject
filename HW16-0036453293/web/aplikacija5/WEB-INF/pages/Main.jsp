<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Main</title>
	</head>
	
	<c:choose>
    <c:when test="${currentUser == null}">
        <p>Not logged in</p>
        <p><a href="/aplikacija5/servleti/call-login">Login</a></p>
        <p><a href="/aplikacija5/servleti/call-register">Register</a></p>
    </c:when>
    <c:otherwise>
       <p>${currentUser.firstName} ${currentUser.lastName}</p>
       <p><a href="/aplikacija5/servleti/logout">Logout</a></p>
    </c:otherwise>
	</c:choose>
	
	<body>
		<p>Registered authors:</p>
		<c:forEach var="user" items="${listOfUsers}">
			<li>
				<a href="/aplikacija5/servleti/author/<c:out value="${user.nick}"/>">${user.nick}</a>
			</li>
		</c:forEach>
		<p>Annonymous users can view all the blog entries and can leave comments. Logged in users can also add new 
			blog entries, edit their old blog entries and delete their blog entries.</p>
	</body>
</html>