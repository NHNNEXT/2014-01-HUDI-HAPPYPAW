<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello jsp</title>
</head>
<body>
	<%
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String emailAddress = request.getParameter("emailAddress");
		
	%>
	Input your information
	<form action = "join_email_list" method="get">
	<table cellspacing="5" cellpadding="5" border="1">
		<tr>
			<td align="right">First name:</td>
			<td><input type = "text" name="first"></td>
			<!-- <%= firstName %></td> -->
		</tr>
		<tr>
			<td align="right">Last name:</td>
			<td><input type ="text" name="last"></td>
			<!-- <%=lastName %></td>-->
		</tr>
		<tr>
			<td align ="right">Email address</td>
			<td><input type="text" name="email"></td>
			
			<!--<%=emailAddress %></td>-->
		</tr>
	</table>
	
		<input type = "submit" value = "Return">
	</form>

</body>
</html>