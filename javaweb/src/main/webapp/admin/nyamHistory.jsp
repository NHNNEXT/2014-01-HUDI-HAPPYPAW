<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import= "model.*, java.util.* " %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	table {
		border: solid 1px black;
	}
	td {
		border: solid 1px black;
	}
</style>
</head>
<body>
	<%
		ArrayList<NyamList> nyamList = (ArrayList<NyamList>)request.getAttribute("nyamList");

	%>
	<table>
		<tr>
			<thead>
				<td>id</td>
				<td>name</td>
				<td>nyamnum</td>
			</thead>
		</tr>
		<%
			for(int i =0; i < nyamList.size(); i++){
		%>
			<tr>
				<td><%=nyamList.get(i).getId() %></td>
				<td><%=nyamList.get(i).getName() %></td>
				<td><%=nyamList.get(i).getNyamNum() %></td>
			</tr>
			 
		<%
			} 
		 %>
	</table>
</body>
</html>