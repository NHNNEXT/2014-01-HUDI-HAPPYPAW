<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="./head.jsp"%>
<link rel="stylesheet" type="text/css" href="/nyam/board/css/boardList.css">

<div class= "content">
<div class="title">정직한 식사 신청</div>
	<table>
		<tr>
			<thead>
				<th name="no">no.</th>
				<th name="writer">글쓴이 </th>
				<th name="title">제목 </th>
				<th name="date">날짜</th>
				<th name="recommend">추천</th>
				<th name="notRecommend">비추천</th>
			</thead>
		</tr>
		<c:forEach items = "${boardList}" var = "writing" >
			<tr class="info">
				<td>${writing.no}</td>
				<td>${writing.userId}</td>
				<td name="title"><a href = "/nyam/admin/boardView?no=${writing.no}">${writing.title}</a></td>
				
				<td>${writing.date} </td>
				<td class="recommend">${writing.recommend }</td>
				<td class="notRecommend">${writing.notRecommend }</td>
		</c:forEach>
			</tr>
	</table>
	
	<footer>
			<ul class="pagination">
				<li><a href="/nyam/board/boardList">처음으로</a></li>
				<%
				int pageNum = (Integer)request.getAttribute("page");
				
				int total = (Integer)request.getAttribute("totalCount");
				int start = pageNum > 5 ? pageNum - 5 : 1;
				int end = (pageNum + 5 - 1) * 15 >= total ? total / 15 + 1 : pageNum + 5;
				
				for(int i = start; i <= end; ++i) {%>
				<li <%= pageNum == i ? "class=\"selected\"" : "" %>><a href="?page=<%=i%>"><%=i %></a></li>
				<%} %>
				<li><a href="?page=<%= total / 15 + 1%>">맨끝으로</a></li>
			</ul>
	</footer>
</div>



<%@ include file="/admin/foot.jsp"%>