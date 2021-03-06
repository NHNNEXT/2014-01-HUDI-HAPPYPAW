<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/user/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/nyam/user/css/historyPeriod.css">
	<div class="title">
	역대 식사 순위</div>
	<div class="list">
<% 
		ArrayList<HashMap<String, Object>> historyPeriod = (ArrayList<HashMap<String, Object>>) request.getAttribute("history");
			 	
				
			for(int i = 0; i < historyPeriod.size(); i++){
				int year = (Integer)historyPeriod.get(i).get("year");
				ArrayList<String> arrayMonth = (ArrayList<String>)historyPeriod.get(i).get("month");
				
		%>
		<p class="year"><%=year%></p>
		<ul class="months hidden">
			<%
					for(int j = 0; j < arrayMonth.size(); j++){
						int month = Integer.parseInt(arrayMonth.get(j));
			%>
			<li class="month"><a href="/nyam/rankingHistory?year=<%=year%>&month=<%=month%>"><%=month+1%> 월</a></li>
				<%
					}
				%>
		</ul> 
			<% 
				}
			%> 
		
	
	</div>
	</section>

	<script>
		var elList = document.querySelector(".list");
		var year = document.querySelectorAll(".year");

		elList.addEventListener("click",function(e) {
			var elTarget = e.target;
			console.log("target:" + e.target);
			for (var a = 0; a < year.length; a++) {
				console.log("year" + year[a]);
				console.log(elTarget);
				if (year[a] === elTarget) {
					var ulHidden = elTarget.nextElementSibling;
					console.log("className:"+ ulHidden.className);
					daylight.each(year, function() {
						this.nextElementSibling.className = "months hidden";
					});
					
					ulHidden.className = "months show";
				
				} else {
					
				}
			}
		});

		var liMonths = document.querySelector(".month");

		liMonths.addEventListener("click", function(e) {

		});
	</script>
<%@ include file="./foot.jsp"%>