<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*, java.util.* "%>

<%@include file="/admin/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/nyam/admin/css/nyamHistory.css"/>


<%
		ArrayList<NyamList> nyamList = (ArrayList<NyamList>) request
				.getAttribute("nyamList");
%>

		<table>
			<tr>
			
				<th class="id">아이디</th>
				<th class="name">이름</th>
				<th class="nyamnum">식사 횟수</th>

			</tr>
			<%
				for (int i = 0; i < nyamList.size(); i++) {
					String id = nyamList.get(i).getId();
					String name = nyamList.get(i).getName();
					int count = nyamList.get(i).getNyamNum();
			%>
			<tr>
				
					<td class="id">
						<a href = "/nyam/admin/individual?studentId=<%=nyamList.get(i).getId()%>">
							<%=id%>
						</a>
					</td>
					<td class="name"><%=name%></td>
					<td class="nyamNum"><%=count%></td>
			</tr>
	
			<%
				}
			%>
		</table>
		<div class="button">
		<form action="/nyam/admin/exportExcel" method="POST">
			<button class="export">export</button>
		</form>
		</div>
		
</section>

<%@include file="/admin/foot.jsp" %>
