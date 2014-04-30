<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../reset.css"/>
<link rel="stylesheet" type="text/css" href="../layout.css"/>
<link rel="stylesheet" type="text/css" href="./css/history.css"/>
<title>넥스트인의 정식</title>
</head>
<body>
	<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
			<p class="title"><%=(String)request.getAttribute("restaurant")%></p>
			<table>
			<tr>
			<th class="date">날짜</th>
			<th>횟수</th>
			</tr>
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

</section>
	<aside>
		<div class="login_box">
			<p>이름: 관리자</p>
			<p><a href="./logout">logout</a></p>
		</div>
		<div class="menu">
			<ul class="menu_ul">
				<li><a href = "/nyam/admin/nyamHistory">메인</a></li>
				<li><a href = "/nyam/admin/manageRest">지정식당 관리</a></li>
				<li><a href = "/nyam/admin/nyamHistory">전체 학생 이용 기록</a></li>
				<li><a href = "/nyam/admin/restaurantHistory">식당별 이용 횟수</a></li>
				<li><a href = "">지정식당 신청 게시판</a></li>
			</ul>
		</div>
	</aside>
</body>
</html>