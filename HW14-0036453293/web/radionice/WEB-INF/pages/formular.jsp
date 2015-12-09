<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Radionica</title>
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
		<h1>
			<c:choose>
				<c:when test="${zapis.id.isEmpty()}">
					Nova radionica
				</c:when>
			</c:choose>
		</h1>
		
		<form action="save" method="post">
		
		<input type="hidden" name="id" value="${zapis.id}">
			
			Naziv <input type="text" name="naziv" value="<c:out value="${zapis.naziv}"></c:out>" size="50"><br>
			<c:if test="${zapis.imaPogresku('naziv')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('naziv')}"></c:out></div>
			</c:if>
			
			Datum <input type="text" name="datum" value="<c:out value="${zapis.datum}"></c:out>" size="15"><br>
			<c:if test="${zapis.imaPogresku('datum')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('datum')}"></c:out></div>
			</c:if>
			
			Oprema <select name="oprema" multiple="multiple" size="10">
			<c:forEach var="oprema" items="${oprema}">
				<option value="${oprema.ID}" ${zapis.oprema.contains(oprema.ID) ? 'selected' : ''}>"${oprema.vrijednost}"</option>
			</c:forEach>
			</select>
			<c:if test="${zapis.imaPogresku('oprema')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('oprema')}"></c:out></div>
			</c:if><br>
			
			Trajanje <select name="trajanje">
			<option value="" disabled>Select one
			<c:forEach var="trajanje" items="${trajanje}">
			<option value="${trajanje.ID}" ${trajanje.ID == zapis.trajanje ? 'selected' : ''}>${trajanje.vrijednost}
			</c:forEach>
					</select><br>
			<c:if test="${zapis.imaPogresku('trajanje')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('trajanje')}"></c:out></div>
			</c:if>
			
			Publika <br><c:forEach var="publika" items="${publika}">
			<input type=checkbox name=publika value="${publika.ID}" ${zapis.publika.contains(publika.ID) ? 'checked' : ''}>${publika.vrijednost}<br>
			</c:forEach>
			<c:if test="${zapis.imaPogresku('publika')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('publika')}"></c:out></div>
			</c:if>
			 
			Broj polaznika <input type="text" name="maksPolaznika" value="<c:out value="${zapis.maksPolaznika}"></c:out>"
			 size="10"><br>
			<c:if test="${zapis.imaPogresku('maksPolaznika')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('maksPolaznika')}"></c:out></div>
			</c:if>
			
			E-Mail <input type="text" name="email" value="<c:out value="${zapis.email}"></c:out>" size="50"><br>
			<c:if test="${zapis.imaPogresku('email')}">
				<div class="greska"><c:out value="${zapis.dohvatiPogresku('email')}"></c:out></div>
			</c:if>
			
			Dopuna <textarea name="dopuna" rows="5" cols="30">${zapis.dopuna}</textarea>
			
			<input type="submit" name="metoda" value="Pohrani">
			<input type="submit" name="metoda" value="Odustani">
		
		</form>
	</body>
</html>