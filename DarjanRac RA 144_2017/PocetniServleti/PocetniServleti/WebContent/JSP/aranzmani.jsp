<html>
<head>
</head>
<body>
   <h1 style="color: blue">
      JSP.Kreiranje aranzmana
   </h1>
   <form method="POST" action="AranzmanServlet">
      <table>
         <tr>
            <td>Broj Aranzmana (id):</td>
            <td><input type="text" name="id_aranzmana" required/></td>
         </tr>      
         <tr>
            <td>Ime i Prezime putnika: </td>
            <td><input type="text" name="full_name"/></td>
         </tr>
         <tr>
            <td>Broj pasosa:</td>
            <td><input type="text" name="passport_number"/></td>
         </tr>
         <tr>
            <td>Naziv destinacije:</td>
            <td><input type="text" name="destination_name"/></td>
         </tr>
         <tr>
			<td>Tip odmora:</td>
			<td><select name="vacation_type">
				  <option value="LETOVANJE">LETOVANJE</option>
				  <option value="ZIMOVANJE">ZIMOVANJE</option>
				  <option value="OBILAZAK_GRADA">OBILAZAK GRADA</option>
			</select></td>
		</tr>
		   <tr>
            <td>Cena:</td>
            <td><input type="text" name="price"/></td>
         </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Posalji"></td>
        </tr>
      </table>
   </form>
</body>
</html>