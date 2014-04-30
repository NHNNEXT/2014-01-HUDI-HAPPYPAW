<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../reset.css">
<link rel="stylesheet" type="text/css" href="../layout.css">
<link rel="stylesheet" type="text/css" href="admin_manageRest.css">
<style>

</style>
</head>
<body>
	<section>
		<header>
			<p class="logo">넥스트인의 정식</p>
		</header>
	<%
		ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");
	%>
	<table>
		<thead>
			<tr>
				<th name="no">NO.</th>
				<th name="short">상호명</th>
				<th name="long">설명</th>
				<th name="long">간단한 주소</th>
				<th rowspan=2 name="qr">QR code</th>
			</tr>
		</thead>
		
			<%
				for(int i = 0; i < restList.size(); i ++){	
					int no = restList.get(i).getNo();
					String name = restList.get(i).getName();
					String desc = restList.get(i).getDesc();
					String location = restList.get(i).getLocation();
					String renew = restList.get(i).getRenew();
			%>
	
				<tr>
					<td><div class="fly"><%=no %></div></td>
					<td><div class="fly"><%=name %></div></td>
					<td><div class="fly"><%=desc %></div></td>
					<td><div class="fly"><%=location %></div></td>
					<td>
						<img src="<%= request.getAttribute("address") + renew + "@" + no %>">
						
						<form action ="/nyam/admin/renewQr" method="POST">
							<button>UPDATE</button>
							<input type="hidden" name = "restaurantNo" value="<%=no%>"/>
						</form>
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