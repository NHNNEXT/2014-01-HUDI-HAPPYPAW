<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>넥스트인의 정식</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css"/>
<link rel="stylesheet" type="text/css" href="./user/css/login.css"/>
</head>
<body>
	<div class="login_form">
		<p class="title">넥스트인의 정식</p>
		<%if(request.getAttribute("error") != "") {%>
			<div class="error-message">
				${error}
			</div>
		<%} %>
		<form action="./login_check" method="POST">
			<input type ="text" name="id" class="id" placeholder="id"><br>
			<input type="password" name="password" class="ps" placeholder="password">
			<button>LOGIN</button>
		</form>
	</div>
</body>
</html>