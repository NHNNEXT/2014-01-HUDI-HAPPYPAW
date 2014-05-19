<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.* , model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>넥스트인의 정식</title>
<link rel="stylesheet" type="text/css" href="./css/reset.css"/>
<link rel="stylesheet" type="text/css" href="./css/layout.css"/>
<link rel="stylesheet" type="text/css" href="./admin/css/history.css"/>
</head>
<body>
<section>

<%
	ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");
	

%><header>
			<p class="logo">넥스트인의 정식</p>
		</header>
		<table>
			<tr>
			<thead>
				<th class="no">NO</th>
				<th class="name">상호</th>
				<th class="desc">설명</th>
				<th class="location">위치</th>
			</thead>
			</tr>
			<%
			for(int i = 0; i < restList.size(); i++){
				String name = restList.get(i).getName();
				String desc = restList.get(i).getDesc();
				String location = restList.get(i).getLocation();
				int no = restList.get(i).getNo();

			
			%>
			<tr>
					<td><%=no%></td>
					<td><%=name%></td>
					<td><%=desc %></td>
					<td><%=location%></td>
			</tr>
	
			<%
				}
			%>
		</table>
<%@include file="./foot.jsp"%>