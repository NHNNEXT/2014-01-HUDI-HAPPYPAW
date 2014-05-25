<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<form action="./login_check" method="POST">
			<input type ="text" name="id" class="id" placeholder="id"><br>
			<input type="password" name="password" class="ps" placeholder="password">
			<button>LOGIN</button>
		</form>
	</div>
</body>
</html>