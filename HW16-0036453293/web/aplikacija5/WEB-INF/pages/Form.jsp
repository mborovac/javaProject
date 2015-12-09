<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blog</title>
		
		<style type="text/css">
		.greska {
			font-family: fantasy;
			font-weight: bold;
			font-size: 0.9em;
			color: #FF0000;
		}
		</style>
		
	</head>
	<body>
	<% String a = (String) request.getAttribute("action"); %>
		<c:choose>
	    <c:when test="${currentUser == null || (selectedBlogOwner != null && currentUser != selectedBlogOwner)}">
	    	<% if(a.equals("edit")) { %> 
	        <p>You are not allowed to edit this blog!</p>
	        <% } else { %>
	        <p>You are not allowed to create a new blog! Log in or register!</p>
	        <% } %>
	        
	        <a href="/aplikacija5/servleti/main">Povratak na main</a>
	    </c:when>
	    <c:otherwise>
			<form action="save" method="post">
			
			<input type="hidden" name="previousPage" value="${previousPage}">
			
			<% if(a.equals("edit") || a.equals("delete")) { %> 
			<input type="hidden" name="entryId" value="${entryId}">
			<% } %>
			
			Blog title: <input type="text" name="title" value="${title}" size="30"><br>
			<c:if test="${titleError != null}">
				<div class="greska"><c:out value="${titleError}"></c:out> </div>
			</c:if>
			Text <input type="text" name="text" value="${text}" size="200"><br>
			<c:if test="${textError != null}">
				<div class="greska"><c:out value="${textError}"></c:out></div>
			</c:if>
			
			<% if(a.equals("new")) { %> 
				<input type="submit" name="metoda" value="Create">
			<% } else if(a.equals("edit")) { %>
				<input type="submit" name="metoda" value="Edit">
		    <% } else {%>
		    	<input type="submit" name="metoda" value="Delete">
		    <% } %>
			<input type="submit" name="metoda" value="Cancel">
			</form>
	    </c:otherwise>
		</c:choose>
	</body>
</html>