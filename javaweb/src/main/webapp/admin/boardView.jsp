<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Board"%>
<%@include file="/admin/head.jsp" %>
	${board}
	no: ${board.writingNo}<br/>
	writer: ${board.userId}<br/>
	title: ${board.title }<br/>
	content: ${board.content }<br/>

	fileName: ${board.fileName }<br/>
	
	recommend: ${recommendInfo.recommend}<br/>
	not recommend: ${recommendInfo.notRecommend }<br/>
	

	<a href = "/nyam/admin/delete?no=${board.writingNo}"><button class="delete">Delete</button></a>

	<%Board board = (Board)request.getAttribute("board"); %>
	<%if(board.getFileName() != null) {%>
		<img src="/images/${board.fileName}" alt="file"/>
	<%} %>

<%@include file="/admin/foot.jsp"%>