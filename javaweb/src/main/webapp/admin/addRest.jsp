<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file ="./head.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/plusRest.css">

	<form action="/nyam/admin/sendRestInfo" method="POST">
		<table>
			<tr>
				<td>상호명 </td>
				<td><input type="text" name="restName" value="${restaurant.name }"/></td>
			</tr>
			<tr>
				<td>설명 </td>
				<td><input type="text" name="desc" value="${restaurant.desc }"/></td>
			</tr>
			<tr>
				<td>위치 </td>
				<td><input type="text" name="location" value="${restaurant.location }"/></td>
			</tr>
		</table>
			<input type="submit" name="submit" value="등록"/>
	</form>


<%@include file ="./foot.jsp"%>