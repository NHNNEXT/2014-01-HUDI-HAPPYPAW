<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.*"%>

<%@include file="./head.jsp" %>
<link rel="stylesheet" type="text/css" href="/nyam/user/css/user_nyamHistory.css">
<script>
	<%
	
	HashMap<String, Integer> map = (HashMap<String, Integer>) request
			.getAttribute("nyamPerDay");
	DateInfo dateinfo = (DateInfo)request.getAttribute("dateinfo");
	int year = dateinfo.getYear();
	int month = dateinfo.getCorrectMonth();
	int dayOfMonth = dateinfo.getDayOfMonth();
	int yoil = dateinfo.getYoil();
	int week = dateinfo.getWeek();
	int day = 1;%>
	var yoil = <%=yoil%>;
	
	var dayOfMonth = <%=dayOfMonth%>;
	var month = <%=month%>;
	var week = <%=week%>;
	var year = <%=year%>;
	$(document).ready(function() {
		var today = new Date();
		var todayMonth = today.getMonth() + 1;
		var todayYear = 1900 +  today.getYear();
		today = today.getDate();
		var stamp = {"-1": 0
		<%for(String date : map.keySet()) {
				out.println(","+date+" : "+map.get(date));
		}%>};
		
		var trWeek = $(".week");
		for(var i = 1; i < week ; i++){
			trWeek.after(trWeek.html());
		}
		var day = 1;
		$(".calendar td").each(function(td, index) {
			
			if(index +1 < yoil){
				return;
			}else if(day > dayOfMonth){
				return;	
			}else{
				var td = $(this);
				if(todayYear === year && month === todayMonth && today === day )
					td.addClass("today");
				
				
				td.find(".day").html(day);
				
				var stamp_area =td.find(".stamp_area");//stamp_print = $(".stamp");
				if(day in stamp){
					var stamp_num = stamp[day];
					for(var i =0; i<stamp_num;i++){
						stamp_area.append('<div class="stamp"></div>');
					}
				}
				++day;
			}
		});
	
		
		
		
	});
</script>



		<div class="calendarBox">

			<div class="month">
				<div class="monthcontrol">
					<a href="/nyam/nyamHistory?month=<%=month - 1%>&year=<%=year %>">◀</a>
					<a href="/nyam/nyamHistory">오늘</a>
					<a href="/nyam/nyamHistory?month=<%=month + 1%>&year=<%=year %>">▶</a>

				</div>
				<h2><%=year%>년
				<%=month%>월</h2>
			</div>
				<%-- <div class="lastMonth arrow">
					<a href="/nyam/nyamHistory?month=<%=month - 1%>&year=<%=year %>">
						<img src = "./img/arrow.png" id = "leftArrow" class="arrow"/>
					</a>
				</div>
				<div class="nextMonth arrow">
				<a href="/nyam/nyamHistory?month=<%=month + 1%>&year=<%=year %>">
					<img src = "./img/arrow.png" id = "rightArrow" class="arrow"/>
				</a>
			</div> --%>
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
			
		</div>
<%@include file="./foot.jsp"%>
