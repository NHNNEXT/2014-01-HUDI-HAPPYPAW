<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>select</title>
</head>
<body>
	<form method ="post" action ="SelectBeer.do">
		select beer characteristics 
		color:
		<select name ="color" size="1">
			<option value ="light"> light</option>
			<option value ="amber"> amber</option>
			<option value ="brown">brown</option>
				
		</select>
		<br><br>
		<center>
			<input type="submit">
		</center>
	</form>
</body>
</html>