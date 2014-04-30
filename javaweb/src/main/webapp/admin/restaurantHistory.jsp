<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="../reset.css"/>
<link rel="stylesheet" type="text/css" href="../layout.css"/>
<link rel="stylesheet" type="text/css" href="./css/history.css"/>
<style>
table tr:hover td {
	background:#eee;
}
</style>
</head>
<body>
	<%
		ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");

	%>
<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
	<table>
	<tr>
		<thead>
			<th>id</td>
			<th>name</td>
			<th>nyamnum</td>
		</thead>
	</tr>
		<%
			for(int i =0; i < restList.size(); i++){
				int no = restList.get(i).getNo();
				String name = restList.get(i).getName() ;
				int count = restList.get(i).getNyamNum() ;
		%>
			<tr onclick="location.href='/nyam/admin/eachRestaurant?restaurantId=<%=no%>';">
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