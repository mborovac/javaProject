<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<style type="text/css">
			table.rez td {text-align: center;}
		</style>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" cellspacing="0" class="rez">
			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
			<tbody>
				<c:forEach var="band" items="${sortedBands}">
					<tr><td>${info[band]}</td><td>${votes[band]}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="/aplikacija2/glasanje-grafika" width="400" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/aplikacija2/glasanje-xls">ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ul>
			<c:forEach var="links" items="${links}">
				<li><a href="${links.value}" target="_blank">${links.key}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>