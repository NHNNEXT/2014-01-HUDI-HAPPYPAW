<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
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
		ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");

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
			for(int i =0; i < restList.size(); i++){
				int no = restList.get(i).getNo();
				String name = restList.get(i).getName() ;
				int count = restList.get(i).getNyamNum() ;
		%>
			<tr>
				<td>
					<a href="/nyam/admin/eachRestaurant?restaurantId=<%=no%>">
						<%=no%>
					</a>
				</td>
				<td><%=name%></td>
				<td><%=count%></td>
			</tr>
			 
		<%
			} 
		 %>
	</table>
</body>
</html>