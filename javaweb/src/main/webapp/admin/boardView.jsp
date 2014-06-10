<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Board"%>
<%@include file="/admin/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/nyam/board/css/pageView.css">
<%
	Board board = (Board) request.getAttribute("board");
%>
<div class="content">
	<div class="title">${board.title }</div>
	<div class="board-info">
		<span class="writer">${board.userId}</span><span class="date"></span>
	</div>
	<div class="desc">
		${board.content } <br />
		<br />
		<%
			if (board.getFileName() != null) {
		%>
		<img src="/images/${board.fileName}" alt="file" />
		<%
			}
		%>

	</div>





	<a href="/nyam/admin/delete?no=${board.writingNo}"><button
			class="delete btn">Delete</button></a>


</div>


<%@include file="/admin/foot.jsp"%>