<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file ="./adminHead.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/addRest.css">

	<form action="/nyam/admin/sendRestInfo" method="POST">
		<table>
			<tr>
				<td>상호명 </td>
				<td><input type="text" name="restName"/></td>
			</tr>
			<tr>
				<td>설명 </td>
				<td><input type="text" name="desc"/></td>
			</tr>
			<tr>
				<td>위치 </td>
				<td><input type="text" name="location"/></td>
			</tr>
		</table>
			<input type="submit" name="submit" value="등록"/>
	</form>


<%@include file ="./adminFoot.jsp"%>