<%-- 에러 페이지 지정과 에러 페이지 작성하기 - 에러를 처리하는 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%-- 
	page 지시자를 사용해 이 페이지가 에러를 처리하는 페이지임을 지정한다. 
--%>
<%@ page isErrorPage="true" %>

<%-- 
	현재 페이지가 정상적으로 처리되었다는 응답 상태 코드로 200을 지정한다. 
--%>
<% response.setStatus(200); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 처리중 에러 발생</title>
</head>
<body>
	<h2>죄송합니다.</h2>
	<p>고객님의 요청을 처리하는 과정에서 아래와 같은 예외가 발생 하였습니다.</p>
	
<%--
	page 지시자의 isErrorPage의 속성이 true로 설정되어 있어야 
	exception 내장 객체를 사용할 수 있다.	
	isErrorPage 속성을 true로 지정하지 않으면 기본값은 false 이다.
--%>
	에러 메시지 : <%= exception.getMessage() %><br/>
	에러 타입 : <%= exception.getClass().getName() %><br/>
</body>
</html>