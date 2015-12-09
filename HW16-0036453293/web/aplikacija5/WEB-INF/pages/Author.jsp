<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Author</title>
	</head>
	
	<c:choose>
    <c:when test="${currentUser == null}">
        <p>Not logged in</p>
    </c:when>
    <c:otherwise>
       <p>${currentUser.firstName} ${currentUser.lastName}</p>
       <p><a href="/aplikacija5/servleti/logout">Logout</a></p>
    </c:otherwise>
	</c:choose>
	
	<body>
		<c:choose>
		<c:when test="${entries.isEmpty() || entries == null}">
			<p>There are no blog entries for user ${nickName}.</p>
		</c:when>
    	<c:otherwise>
			<p>Blog entries for user ${nickName}:</p>
			<c:forEach var="entry" items="${entries}">
				<li>
					<a href="/aplikacija5/servleti/author/${nickName}/${entry.id}">${entry.title}</a>&emsp;&emsp;
					<c:choose>
					<c:when test="${currentUser.nick == nickName}">
						<a href="/aplikacija5/servleti/author/${currentUser.nick}/edit/${entry.id}">Edit</a>&emsp;
						<a href="/aplikacija5/servleti/author/${currentUser.nick}/delete/${entry.id}">Delete</a>
			    	</c:when>
			    	</c:choose>
				</li>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${currentUser != null}">
	        <p><a href="/aplikacija5/servleti/author/${currentUser.nick}/new">Add a new blog entry</a></p>
    	</c:when>
    	</c:choose>
	</body>
</html>