<%@page import="beans.Aranzman" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="aranzmani" class="dao.AranzmanDAO" scope="application"/>
<html>
	<head>
	</head>
	<body>
	   <h1 style="color: blue">
	      JSP.Pregled Aranzmana
	   </h1>
	   <table border="1">
			<thead>
				<th>Broj aranzmana:</th>
				<th>Puno ime putnika:</th>
				<th>Broj pasosa:</th>
				<th>Naziv destinacije:</th>
				<th>Tip odmora: </th>
				<th>Cena:</th>
				<th></th>
			</thead>
			<tbody>
			
				<c:forEach var="aranzman" items="${aranzmani.findAll()}">
				  <c:if test="${aranzman.confirmed == true }" var="condition">
				   
				    <tr style="background-color: blue" >		
						<td>${aranzman.id_aranzmana}</td>
						<td>${aranzman.full_name}</td>
						<td>${aranzman.passport_number}</td>
						<td>${aranzman.destination_name}</td>
						<td>${aranzman.vacation_type}</td>
						<td>${aranzman.price}</td>
						<td>Potvrdjeno</td>
					</tr>	  
				  </c:if>
				  <c:if test="${!condition}">
				     <tr>		
						<td>${aranzman.id_aranzmana}</td>
						<td>${aranzman.full_name}</td>
						<td>${aranzman.passport_number}</td>
						<td>${aranzman.destination_name}</td>
						<td>${aranzman.vacation_type}</td>
						<td>${aranzman.price}</td>
						  
				          <c:if test="${aranzman.confirmed == false}">
				              <td><a href="AranzmanServlet?ID=${aranzman.id_aranzmana}">Potvrda aranzmana</a></td>
				          </c:if>
					</tr>	
				  </c:if>	
				</c:forEach>
			</tbody>
		</table>
		
		
		
		
		
		
	</body>
</html>