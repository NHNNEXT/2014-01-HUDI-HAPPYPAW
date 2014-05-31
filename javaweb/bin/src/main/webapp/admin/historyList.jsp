<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/reset.css" />
<link rel="stylesheet" type="text/css" href="../css/layout.css" />
<link rel="stylesheet" type="text/css"
	href="./css/admin_historyList.css" />
<title>Insert title here</title>
</head>
<body>
	<div style="float: left;"></div>
	<section> <header>

	<p class="logo">넥스트인의 정식</p>
	</header>

	<div class="list">
		<%
			HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) request.getAttribute("history");
			 	
				for(String key : map.keySet()){
		%>
		<p class="year"><%=key%></p>
		<ul class="months hidden">
			<%
				for(int i =0; i<map.get(key).size(); i++){
								int month = Integer.parseInt(map.get(key).get(i)) + 1;
			%>
			<li class="month"><a href="/nyam/admin/nyamHistory?year=<%=key%>&month=<%=month%>"><%=month%> 월</a></li>
			<%
				}%>
		</ul>
				<% 
							}
			%>
		
	</div>
	</div>
	</section>

	<aside>
	<div class="login_box">
		<p>이름: 관리자</p>
		<p>
			<a href="./logout">logout</a>
		</p>
	</div>
	<div class="menu">
		<ul class="menu_ul">
			<li><a href="/nyam/admin/nyamHistory">메인</a></li>
			<li><a href="/nyam/admin/manageRest">지정식당 관리</a></li>
			<li><a href="/nyam/admin/nyamHistory">전체 학생 이용 기록</a></li>
			<li><a href="/nyam/admin/restaurantHistory">식당별 이용 횟수</a></li>
			<li><a href="">지정식당 신청 게시판</a></li>
		</ul>
	</div>
	</aside>
	<script>
	    //load 된 이후에 동작되도록 하기. 
	        // window.addEventListener('load', function() {
	    	// //main...
	    	// //필요한 함수를 만들어서 여기서는 그 함수를 호출만한다.
	    	// })
	    	
	    
		var elList = document.querySelector(".list");
		var year = document.querySelectorAll(".year");

		elList.addEventListener("click",function(e) {
			var elTarget = e.target;
			console.log("target:" + e.target);
			for (var a = 0,yearLen = year.length; a < yearLen ; a++) {
				console.log("year" + year[a]);
				console.log(elTarget);
				if (year[a] === elTarget) {
					var ulHidden = elTarget.nextElementSibling;
					console.log("className:"+ ulHidden.className);
					ulHidden.className = (ulHidden.className == "months show" ? "months hidden": "months show");
				}
			}
		});

		var liMonths = document.querySelector(".month");

		liMonths.addEventListener("click", function(e) {

		});
	</script>
</body>
</html>