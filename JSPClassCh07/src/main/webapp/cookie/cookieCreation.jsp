<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Cookie cookie = new Cookie("id", "king");

	cookie.setMaxAge(60 * 3);

	response.addCookie(cookie);
	response.addCookie(new Cookie("name", "John"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	쿠키 이름 : <%= cookie.getName() %><br/>
	쿠키 값 : <%= cookie.getValue() %><br/>
	쿠키 유효기간 : <%= cookie.getMaxAge() %>초<br/>
</body>
</html>