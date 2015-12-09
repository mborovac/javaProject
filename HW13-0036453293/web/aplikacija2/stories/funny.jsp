<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%  
String textColor = "";  
Double rand = Math.random();
if(rand < 0.3) {
	textColor = "green";  
}

else if(rand > 0.3 && rand < 0.5) {
	textColor = "blue";  
}

else if(rand > 0.5 &&  rand < 0.8) {
	textColor = "red";  
}

else {
	textColor = "yellow";
}

String color = "white";
if(session.getAttribute("pickedBgCol") != null) {
	color = session.getAttribute("pickedBgCol").toString();
}
%> 

<html>

	<head></head>
	
	<body bgcolor="<%= color %>">
		<p>
			<font color="<%= textColor %>">
			My sister, Paula, and her husband, Chris, had just finished tucking their young ones into bed one evening 
			when they heard crying coming from the children's room. Rushing in, they found Tommy crying hysterically.
			He had accidentally swallowed a 5p piece and was sure he was going to die.  No amount of talking could change 
			his mind. Trying to calm him, Chris palmed a 5p coin that he happened to have in his pocket and pretended to 
			remove it from Tommy's ear. Tommy, naturally, was delighted.  In a flash, he snatched it from his father's hand, 
			swallowed it and demanded cheerfully - 'Do it again, Dad!'
			</font>
		</p>
	</body>
	
</html>