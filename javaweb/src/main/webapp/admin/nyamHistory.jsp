<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*, java.util.* "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>넥스트인의 정식</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css"/>
<link rel="stylesheet" type="text/css" href="../css/layout.css"/>
<link rel="stylesheet" type="text/css" href="./css/history.css"/>

</head>
<%
		ArrayList<NyamList> nyamList = (ArrayList<NyamList>) request
				.getAttribute("nyamList");
%>
<body>
	
	<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
		<table>
			<tr>
			<thead>
				<th class="id">아이디</th>
				<th class="name">이름</th>
				<th class="nyamnum">식사 횟수</th>
			</thead>
			</tr>
			<%
				for (int i = 0; i < nyamList.size(); i++) {
					String id = nyamList.get(i).getId();
					String name = nyamList.get(i).getName();
					int count = nyamList.get(i).getNyamNum();
			%>
			<tr>
				
					<td>
						<a href = "/nyam/admin/individual?studentId=<%=nyamList.get(i).getId()%>">
							<%=id%>
						</a>
					</td>
					<td><%=name%></td>
					<td><%=count%></td>
			</tr>
	
			<%
				}
			%>
		</table>
		<form action="/nyam/admin/exportExcel" method="POST">
			<button class="export">export</button>
		</form>
		
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