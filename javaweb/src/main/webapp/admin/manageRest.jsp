<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*, java.util.*"%>
<!DOCTYPE html>
<%@include file="./head.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/admin_manageRest.css">


		<%
			ArrayList<Restaurant> restList = (ArrayList<Restaurant>)request.getAttribute("restList");
		%>

		 	<a href="/nyam/admin/addRestaurant">
				<input type="button" name="addRest" value="식당 추가">
			</a>
			<br/>
		<table>
			<thead>
				<tr>
					<th name="no">NO.</th>
					<th name="short">상호명</th>
					<th name="long">설명</th>
					<th name="long">간단한 주소</th>
					<th rowspan=2 name="qr">QR code</th>
					<th name="control">관리</th>
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
						<td name="qr">
							<img src="<%= request.getAttribute("address") + renew + "@" + no %>">	
						</td>
						<td>
							<form action ="/nyam/admin/renewQr" method="POST">
								<button>UPDATE</button>
								<input type="hidden" name = "restaurantNo" value="<%=no%>"/>
							</form>
							<a href="/nyam/admin/restModify?no=<%=no%>"><input type="button" name="modify" value="수정"></a>
							<a href="/nyam/admin/restDelete?no=<%=no%>"><input type="button" name="delete" value="삭제"></a>
						</td>
						
					</tr>
					 
					<%
						} 
					 %>
			</table>

<%@include file="./foot.jsp" %>