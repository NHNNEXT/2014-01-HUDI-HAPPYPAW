<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
			<td>desc</td>
			<td>location</td>
		</thead>
	</tr>
		<%
		for(int i = 0; i < restList.size(); i ++){	

		%>

			<tr>
				<td><%=restList.get(i).getNo() %></td>
				<td><%=restList.get(i).getName() %></td>
				<td><%=restList.get(i).getDesc() %></td>
				<td><%=restList.get(i).getLocation() %></td>
				<td>
					<img src="<%=request.getAttribute("address") +  restList.get(i).getRenew() + "@" + restList.get(i).getNo()%>">
					
				</td>
				<td>
					<form action ="/nyam/admin/renewQr" method="POST">
						<button>UPDATE</button><input type="hidden" name = "restaurantNo" value="<%=restList.get(i).getNo()%>"/>
					</form>
				</td>
			</tr>
			 
		<%
			} 
		 %>
	</table>
</body>
</html> 