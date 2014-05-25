<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../user/userHead.jsp"%>

<link rel="stylesheet" type="text/css" href="./css/writing.css">

	<div class="formBox">
		<form action="/nyam/board/sendContent" method="POST"
			enctype="multipart/form-data">
			<div class="writing">
				<input type="textbox" name="title"  placeholder="Title"><br /> 
				
				<textarea name="content" placeholder="Content">
				</textarea><br/>
			</div>
			<div class="filebox">
				<input type="file" name="file"><br/>
			</div>
			<div class="button">
				<input type="submit" value="SEND">
			</div>
			
		</form>
	</div>

<%@include file="../user/userFoot.jsp"%>