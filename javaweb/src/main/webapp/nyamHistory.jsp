<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.*"%>
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
</body>
</html> 