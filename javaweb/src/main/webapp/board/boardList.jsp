<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../user/userHead.jsp"%>
<link rel="stylesheet" type="text/css" href="/nyam/board/css/boardList.css">

<div class= "content">
	<table>
		<tr>
			<thead>
				<th name="no">no.</th>
				<th name="writer">글쓴이 </th>
				<th name="title">제목 </th>
				<th name="date">날짜</th>
				<th name="recommend">추천</th>
				<th name="notRecommend">비추천</th>
				<th name="read">조회수</th>
			</thead>
		</tr>
		<c:forEach items = "${boardList}" var = "writing" >
			<tr>
				<td>${writing.writingNo}</td>
				<td>${writing.userId}</td>
				<td name="title"><a href = "/nyam/board/view?no=${writing.writingNo}">${writing.title}</a></td>
				
				<td>날짜 입력</td>
				<td>1</td>
				<td>1</td>
				<td>234</td>
			</tr>
		</c:forEach>
	</table>
	<a href = "/nyam/board/writing"><button>글쓰기</button></a>
</div>















<%@ include file="../user/userFoot.jsp"%>