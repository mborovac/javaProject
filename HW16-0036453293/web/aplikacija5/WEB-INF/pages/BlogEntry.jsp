<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blog entry</title>
		
		<style type="text/css">
		.greska {
			font-family: fantasy;
			font-weight: bold;
			font-size: 0.9em;
			color: #FF0000;
		}
		</style>
		
	</head>
		
	<c:choose>
    <c:when test="${currentUser == null}">
        <p>Not logged in</p>
    </c:when>
    <c:otherwise>
       <p>${currentUser.firstName} ${currentUser.lastName}</p>
       <% String previous = (String) request.getAttribute("previousPage"); %>
       <% if(previous != null && !previous.isEmpty()) { %> 
       <p><a href="/aplikacija5/servleti/logout?returnTo=${previousPage}">Logout</a></p>
       <% } else { %>
       <p><a href="/aplikacija5/servleti/logout">Logout</a></p>
       <% } %>
    </c:otherwise>
	</c:choose>
	
	<body>
		<h1>${blogEntry.title}</h1>
		<c:choose>
		<c:when test="${currentUser != null && currentUser.nick == nickName}">
			<a href="/aplikacija5/servleti/author/${currentUser.nick}/edit/${blogEntry.id}">Edit blog</a>
    	</c:when>
    	</c:choose>
		<h2>Tekst bloga:</h2>
		<p>${blogEntry.text}</p>
		<p><i>Komentari:</i></p>
		<ol>
		<c:forEach var="comment" items="${blogEntry.comments}">
			<li><div style="font-weight: bold">[Korisnik=${comment.usersEMail}] ${comment.postedOn}</div><div style="padding-left: 10px;">${comment.message}</div></li>
		</c:forEach>
		</ol>
		
		<p><i>Novi komentar:</i></p>
		<form action="save" method="post">
			
			<input type="hidden" name="entryId" value="${blogEntry.id}">
			
			Message: <input type="text" name="message" value="${message}" size="60"><br>
			<c:if test="${messageError != null}">
				<div class="greska"><c:out value="${messageError}"></c:out></div>
			</c:if>
			<br>
			E-mail <input type="text" name="email" value="${email}" size="30"><br>
			<c:if test="${emailError != null}">
				<div class="greska"><c:out value="${emailError}"></c:out></div>
			</c:if>

			<input type="submit" name="metoda" value="Comment">
			</form>
	</body>
</html>