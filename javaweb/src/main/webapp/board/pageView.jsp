<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Board"%>
<%@include file="/user/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/nyam/board/css/pageView.css">
<%Board board = (Board)request.getAttribute("board"); %>
<div class= "content">
<div class="title">${board.title }</div>
<div class="board-info"><span class="writer">${board.userId}</span><span class="date"></span></div>
<div class="desc">
${board.content }

<br/><br/>
	<%if(board.getFileName() != null) {%>
		<img src="/images/${board.fileName}" alt="file"/>
	<%} %>

</div>


	

<div class="like-area">
<a href="/nyam/board/recommend?no=${board.writingNo}" class="like">${recommendInfo.recommend}</a>
<a href="/nyam/board/notRecommend?no=${board.writingNo}" class="dislike">${recommendInfo.notRecommend}</a>
</div>
	
	<%
	User check_user = User.getLoginuser(session); 
	%>
	<% if(board.getUserId().equals(check_user.getId())) {%>
		<a href = "/nyam/board/delete?no=${board.writingNo}"><button class="delete btn">Delete</button></a>
		<a href = "/nyam/board/modify?no=${board.writingNo}"><button class="modify btn">Modify</button></a>
	<%} %>
	

</div>

<%@include file="/user/foot.jsp"%>