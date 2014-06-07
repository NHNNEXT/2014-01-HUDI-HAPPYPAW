<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/admin/head.jsp" %>

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

	<%@include file="/admin/foot.jsp" %>
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