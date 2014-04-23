<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.*"%>
	<!-- DTD에 대해서는 무엇인지는 한번 알아보세요. HTML5 DTD 는 아주 심플하고 당장사용할 수 있음 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		ArrayList<StampHistory> stampList = (ArrayList<StampHistory>) request
				.getAttribute("record");
		for (int i = 0; i < stampList.size(); i++) {
			out.println(stampList.get(i));
		}
	%>
	<br/>
	<a href ="./logout">logout</a>
</body>
</html> 