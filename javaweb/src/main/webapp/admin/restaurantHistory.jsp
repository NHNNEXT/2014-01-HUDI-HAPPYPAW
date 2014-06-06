<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
<%@include file= "./head.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/restaurantHistory.css"/>

	<%
		ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");

	%>

	<table>
		<tr>

			
				<th class="no">번호</td>
				<th class="name">식당명</td>
				<th class="nyamNum">식사 횟수</td>
				

		</tr>
		<%
			for(int i =0; i < restList.size(); i++){
				int no = restList.get(i).getNo();
				String name = restList.get(i).getName() ;
				int count = restList.get(i).getNyamNum() ;
		%>

			<tr onclick="location.href='/nyam/admin/restPeriod?restaurantId=<%=no%>';">
				<td class="no"><%=no%></td>
				<td class="name"><%=name%></td>
				<td class="nyamNum"><%=count%> 회</td>
			</tr>
			 
		<%
			} 
		 %>

	</table>
	
	
<%@include file = "./foot.jsp"%>