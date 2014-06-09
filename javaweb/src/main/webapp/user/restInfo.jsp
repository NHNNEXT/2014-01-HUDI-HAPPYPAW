<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/user/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/nyam/user/css/restInfo.css"/>
	<div class="box">
		<span class="title"> ${rest.name }</span></tr>
	<table>
	
		<tr>
			<td class="info">설명 </td>
			<td class="desc">${rest.desc }</td>
		</tr>
		<tr>
			<td class="info">위치</td>
			<td class="desc">${rest.location }</td>
		</tr>
	</table>
	</div>

<%@include file="/user/foot.jsp"%>