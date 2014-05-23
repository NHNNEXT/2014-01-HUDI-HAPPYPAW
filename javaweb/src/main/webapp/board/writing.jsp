<%@include file="../head.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="./css/user_nyamHistory.css">

<form action="/nyam/board/sendContent" method="POST"
	enctype="multipart/form-data">
	제목: <input type="textbox" name="title"><br /> 내용:
	<textarea cols="50" rows="20" name="content">
	</textarea>
	
<!-- 	<div id="fileUploader_2" class="fileUploader">
		File upload zone
		
		<div class="fileListArea ">
			<select id="uploaded_file_list_2" multiple="multiple" class="fileList" title="Attached File List">
				<option></option>
			</select>
		</div>
		<div class="fileUploadControl">
			<button type="button" id="swfUploadButton2" class="text">파일
				첨부</button>
			<button type="button" onclick="removeUploadedFile('2');" class="text">선택
				삭제</button>
			<button type="button" onclick="insertUploadedFile('2');" class="text">본문
				삽입</button>
		</div>
	</div> -->
	<input type="file" name="file"  multiple="multiple"><br>
	<br/><input type="submit" value="SEND">
	<br/> <input type="reset" value="RESET">
</form>

<%@include file="../foot.jsp"%>