<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>

<%@ page isErrorPage="true" %>

<% response.setStatus(HttpServletResponse.SC_OK); %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NullPointerException 처리</title>
<style>
	p { 
		font-size: 12px; 
	}
</style>
</head>
<body>
	<h2>서버 오류</h2>
	<div>
		<p>요청을 처리하는 중 아래와 같은 에러가 발생하였습니다.</p>
		<%-- exception 내장 변수를 이용해 발생된 Exception의 이름을 출력하고 있다. --%>
		<p>에러 타입 : <%= exception.getClass().getName() %></p>
		<p><a href="#">고객센터 문의하기</a></p>
	</div>
</body>
</html>