<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Radionice</title>
	</head>
	
	<c:choose>
    <c:when test="${user == null}">
        <p>Anonimni korisnik</p>
        <p><a href="login-special">Login</a></p>
    </c:when>
    <c:otherwise>
       <p>${user.getPotpuniNaziv()}</p>
       <p><a href="logout">Logout</a></p>
    </c:otherwise>
	</c:choose>
	
	<body>
		<h1>Lista postojećih radionica</h1>
		<c:choose>
			<c:when test="${radionice.isEmpty()}">
				<p>Nema radionica</p>
			</c:when>
			<c:otherwise>
				<ol>
					<c:forEach var="radionica" items="${radionice}">
						<li>
							<c:out value="${radionica.naziv}"></c:out>
							<c:out value="${radionica.datum}"></c:out>
							<a href="edit?id=<c:out value="${radionica.id}"/>">Uredi</a>
						</li>
					</c:forEach>
				</ol>
			</c:otherwise>
		</c:choose>
		<p><a href="new">Novi</a></p>
	</body>
</html>