<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${board}
	no: ${board.writingNo}<br/>
	writer: ${board.userId}<br/>
	title: ${board.title }<br/>
	content: ${board.content }<br/>

	fileName: ${board.fileName }<br/>
	
	recommend: ${recommendInfo.recommend}<br/>
	not recommend: ${recommendInfo.notRecommend }<br/>
	
	<a href = "/nyam/board/recommend?no=${board.writingNo}"><button class="recommend">LIKE</button></a>
	<a href = "/nyam/board/notRecommend?no=${board.writingNo}"><button class="recommend">DISLIKE</button></a>

</body>
</html>