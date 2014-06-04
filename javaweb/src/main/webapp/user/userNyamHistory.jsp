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
	console.log(yoil);
	var day = 1;
	var dayOfMonth = <%=dayOfMonth%>
	var week = <%=week%>
	$(document).ready(function() {
		var stamp = {"-1": 0
		<%for(String date : map.keySet()) {
				out.println(","+date+" : "+map.get(date));
		}%>};
		
		var trWeek = $(".week");
		for(var i = 1; i < week ; i++){
			trWeek.after(trWeek.html());
		}
		
		$(".calendar td").each(function(td, index) {
			if(index +1 < yoil){
				return;
			}else if(day > dayOfMonth){
				return;	
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



		<article>
	
			<div class="month">
				<%=year%>년
				<%=month%>월
			</div>
			

		</article>
		<div class="calendarBox">
				<div class="lastMonth arrow">
					<a href="/nyam/nyamHistory?month=<%=month - 1%>&year=<%=year %>">
						<img src = "./img/arrow.png" id = "leftArrow" class="arrow"/>
					</a>
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
			<div class="nextMonth arrow">
				<a href="/nyam/nyamHistory?month=<%=month + 1%>&year=<%=year %>">
					<img src = "./img/arrow.png" id = "rightArrow" class="arrow"/>
				</a>
					
			</div>
		</div>
<%@include file="./foot.jsp"%>
