<%-- include 지시자를 이용해 다른 JSP 페이지 포함하기 - 부모 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	
	String book = "백견불여일타 JSP&Servlet";
	String image = "images/jsp&servlet.jpg";
	int price = 27000;
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>include 지시자 사용하기</title>
</head>
<body style="font-size: 12px;">
	<h2>도서 정보 보기</h2>	
	<%-- 
		include 지시자를 사용하여 컴파일 타임에 이 위치에 다른 JSP페이지를 포함 할 수 있다.
	--%>	
	<%@ include file="jspIncludeDirectiveSub.jsp" %>	
</body>
</html>