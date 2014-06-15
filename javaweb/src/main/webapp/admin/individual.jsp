<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.*"%>
<%@include file="/admin/head.jsp" %>
<link rel="stylesheet" type="text/css"
	href="/nyam/user/css/user_nyamHistory.css" />
			<script>
		<%HashMap<String, Integer> map = (HashMap<String, Integer>) request
				.getAttribute("nyamPerDay");
		
		DateInfo info = (DateInfo)request.getAttribute("dateInfo");
		String id = (String)request.getAttribute("id");
		String name = (String)request.getAttribute("name");	
		
		int year = info.getYear();
		int month =  info.getMonth();
		int dayOfMonth = info.getDayOfMonth();
		int yoil = info.getYoil();
		int week = info.getWeek();
		int day = 1;%>
		var yoil = <%=yoil%>;
		console.log(yoil);
		var day = 1;
		var dayOfMonth = <%=dayOfMonth%>
		var week = <%=week%>
		
		$(document).ready(function() {
			var stamp = {"-1": 0
			<%for(String date : map.keySet()) {
					out.println(","+date+" : "+map.get(date));
			}%>
			
			};
			
			var trWeek = $(".week");
			for(var i = 1; i < week ; i++){
				trWeek.after(trWeek.html());
			}
			
			$(".calendar td").each(function(td, index) {
				if(index +1 < yoil){
					return;
				}else if(day > dayOfMonth){
					return;  //위에것이랑 합치기.	
				}else{
					
					$(this).find(".day").html(day);
					
					var stamp_print = $(".stamp");
					if(day in stamp){
						var stamp_num = stamp[day];
						for(var i =0; i<stamp_num;i++){
							$(this).find(".stamp_area").append('<div class="stamp"></div>');
							console.log("test");
						}
					}
					++day;
				}
			});
		});
	</script>
	
	</head>
	
	<body>
	

		<section> 

			<article>
				<div class="title"><%=id%>
					<%=name%></div>
				<div class="month"><%=year%>년
					<%=month + 1%>월
				</div>
		
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
					<tr class="week">
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
						<td><div class="day"></div>
							<div class="stamp_area"></div></td>
					</tr>
		
				</tbody>
		
			</table>
				</article>
		</section>
<%@include file="/admin/foot.jsp"%>