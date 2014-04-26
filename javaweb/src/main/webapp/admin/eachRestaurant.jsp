<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
			<table>
	<%
		HashMap<String, Integer> map = (HashMap<String, Integer>)request.getAttribute("map");
		for(String date : map.keySet()){
			int count = map.get(date);
	%>
				<tr>
					<td><%= date %>
					</td>
					<td><%= count %>
					</td>
				</tr>
	<% 
		}
	%>
			</table>
</body>
</html>