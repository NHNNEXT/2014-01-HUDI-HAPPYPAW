<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="java.io.*, database.*, model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

/* 	DAO dao = DAO.getInstance();
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String usersId = (String) session.getAttribute("users_id");
	String file; 
	Board board;
	if (request.getParameter("file") != null) {
		file = request.getParameter("file");
		 board = new Board(title, content, file, usersId);
		 
	} else {
		// 일단 파일 없이 하는
		 board = new Board(title, content, usersId);
	}
	dao.insertBoard(board); */
	
	
	//file 읽기 
	String contentType = request.getContentType();
	DataInputStream in = new DataInputStream(request.getInputStream());
	
	int formDataLength = request.getContentLength();
	out.println("length : " + formDataLength + "<br/>");
	byte arrDataBytes[] = new byte[formDataLength];
	int byteRead = 0;
	int totalBytesRead = 0;
	
	while(totalBytesRead < formDataLength){
		byteRead = in.read(arrDataBytes, totalBytesRead, formDataLength);
		totalBytesRead +=byteRead;
	}
	
	String file = new String(arrDataBytes);
	out.println(file);
	String saveFile = file.substring(file.indexOf("filename=\"") + 10);
    /*
    saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
    saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+ 1, saveFile.indexOf("\""));
    int lastIndex = contentType.lastIndexOf("=");
    String boundary = contentType.substring(lastIndex + 1, contentType.length());
    int pos;
    pos = file.indexOf("filename=\"");
    pos = file.indexOf("\n", pos) + 1;
    pos = file.indexOf("\n", pos) + 1;
    pos = file.indexOf("\n", pos) + 1;
    int boundaryLocation = file.indexOf(boundary, pos) - 4;
    int startPos = ((file.substring(0, pos)).getBytes()).length;
    int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
    FileOutputStream fileOut = new FileOutputStream(saveFile);
    fileOut.write(arrDataBytes, startPos, (endPos - startPos));
    fileOut.flush();
    fileOut.close(); */
%>
</body>
</html>