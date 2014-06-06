<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, model.*"%>
<%@include file= "./head.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/eachRestaurant.css"/>

			<p class="title"><%=(String)request.getAttribute("restaurant")%></p>
			<table>
			<tr>
			<th class="date">날짜</th>
			<th class="nyamNum">횟수</th>
			</tr>
	<%
		ArrayList<HashMap<String, String>> array = (ArrayList<HashMap<String, String>>)request.getAttribute("array");

	for(int i = 0; i < array.size(); i++){
			HashMap<String, String> map = array.get(i);
			
				String date = map.get("stampDate");
				String num = map.get("nyamNum");
	%>
				<tr>
					<td class="date"><%= date %>
					</td>
					<td class="nyamNum"><%= num %>
					</td>
				</tr>
	<% 
			
		}
	%>
			</table>

<%@include file = "./foot.jsp"%>