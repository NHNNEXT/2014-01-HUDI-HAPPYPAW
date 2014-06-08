<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.* , model.*"%>
<%@include file="/user/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/nyam/admin/css/restaurant.css"/>


<%
	Restaurant restaurant = (Restaurant)request.getAttribute("restaurant");
	

%>
<div class="title">넥스트인의 정직한 식사</div>
<%@include file="/user/foot.jsp"%>