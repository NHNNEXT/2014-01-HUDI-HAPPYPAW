<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
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
	<%
	ArrayList<HashMap<String, String>> nyamRanking = (ArrayList<HashMap<String, String>>)request.getAttribute("nyamRanking");
	int count = 0;
	int year = (Integer)request.getAttribute("year");
	int month  = (Integer)request.getAttribute("month");
	
	%>
	
	
	<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
		<div class="date"><%=year %>년 <%=month+1 %>월 </div>
		<table>
			<tr>
			<thead>
				<th class="ranking">순위</th>
				<th class="id">아이디</th>
				<th class="name">이름</th>
				<th class="nyamnum">식사 횟수</th>
			</thead>
			</tr>
			<%
				for(int i = 0; i < nyamRanking.size(); i++){
					count++;
					String id = nyamRanking.get(i).get("id");
					String num = nyamRanking.get(i).get("nyamNum");
					String name = nyamRanking.get(i).get("name");
			%>
			<tr>
					<td><%=count %></td>
					<td>
						<a href = "/nyam/individual?studentId=<%=nyamRanking.get(i).get("id")%>&year=<%=year%>&month=<%=month%>">
							<%=id%>
						</a>
					</td>
					<td><%=name %></td>
					<td><%=num%></td>
			</tr>
	
			<%
				}
			%>
		</table>
		
<%@include file="./foot.jsp"%>