<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="reset.css">
<link rel="stylesheet" type="text/css" href="user_nyamHistory.css">
<script src="./js/daylight.js"></script>
<script>
<%
HashMap<String, Integer> map = (HashMap<String, Integer>) request
		.getAttribute("nyamPerDay");
int year = (Integer)request.getAttribute("year");
int month = (Integer)request.getAttribute("month");
int dayOfMonth = (Integer)request.getAttribute("dayOfMonth");
int yoil = (Integer)request.getAttribute("yoil");
int week = (Integer)request.getAttribute("week");
int day = 1;
%>
var yoil = <%=yoil%>;
var day = 1;
$(document).ready(function() {
	var stamp = {"-1": 0
	<%
	for(String date : map.keySet()) {
			out.println(","+date+" : "+map.get(date));
	}
	%>};
	$(".calendar td").each(function(td, index) {
		if(index < yoil)
			return;
		console.log(index+"   "+day);
		$(this).find(".day").html(day);
		++day;
	});
});
</script>

</head>

<body>




	<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
		<article>
			<div class="month"><%=month+1 %>월</div>
		</article>
		<table class="calendar">
		<thead>
			<tr id="record">
				<th>SUN</th>
				<th>MON</th>
				<th>TUE</th>
				<th>WED</th>
				<th>THR</th>
				<th>FRI</th>
				<th>SAT</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			<tr>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
				<td><div class="day"></div><div class="stamp_area"></div></td>
			</tr>
			</tbody>

		</table>
	</section>
	<aside>
		<div class="login_box">
			학번: <%=	request.getAttribute("session")%><!-- id --><br>
			이름: <%= request.getAttribute("name") %><br/>
			
			<a href="./logout">logout</a>
		</div>
		<div class="menu">
			<div class="menu_text">		
				Menu
			</div>	
			<ul class="menu_ul">
				<li><a href = "/nyam/nyamHistory">나의 식사기록</a></li>
				<li><a href = "">나의 식사순위</a></li>
				<li><a href = "">지정식당 목록</a></li>
				<li><a href = "">지정식당 신청 게시판</a></li>
			</ul>
		</div>
	</aside>

</body>
</html>
