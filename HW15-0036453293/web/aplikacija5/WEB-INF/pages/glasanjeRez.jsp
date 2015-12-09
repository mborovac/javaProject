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
			<thead><tr><th>Naziv opcije</th><th>Broj glasova</th></tr></thead>
			<tbody>
				<c:forEach var="voteOption" items="${results}">
					<tr><td>${voteOption.optionTitle}</td><td>${voteOption.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="/aplikacija5/servleti/glasanje-grafika?pollID=${pollID}" width="400" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="/aplikacija5/servleti/glasanje-xls?pollID=${pollID}">ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Poveznice na materijale pobjednika:</p>
		<ul>
			<c:forEach var="highestVotedOptions" items="${highestVotedOptions}">
				<li><a href="${highestVotedOptions.optionLink}" target="_blank">${highestVotedOptions.optionTitle}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>