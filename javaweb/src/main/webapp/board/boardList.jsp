<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../user/head.jsp"%>
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
				<td name="title"><a href = "/nyam/board/view?no=${writing.no}">${writing.title}</a></td>
				
				<td>${writing.date} </td>
				<td name="recommend">${writing.recommend }</td>
				<td name="not_recommend">${writing.notRecommend }</td>
		</c:forEach>
			</tr>
	</table>
	<a href = "/nyam/board/writing"><button>글쓰기</button></a>
</div>



<%@ include file="../user/foot.jsp"%>