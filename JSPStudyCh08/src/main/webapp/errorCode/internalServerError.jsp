<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	현재 페이지가 정상적으로 처리되었다는 응답 상태 코드를 설정하고 있다.
	정상처리에 대한 HTTP 응답 코드는 200으로 아래와 같이 상수로 지정하면 된다. 
	HttpServletResponse.SC_OK는 상수로 200으로 설정되어 있다.
--%>    
<% response.setStatus(HttpServletResponse.SC_OK); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InternalServerError 처리</title>
<style>	
	p { font-size: 12px; }
</style>
</head>
<body>
	<h2>서버 오류</h2>
	<div>		
		<p>서버에서 요청을 처리하는 중 내부적인 에러가 발생하여,<br/>
			고객님의 요청을 정상적으로 처리할 수 없습니다.</p>
		<p><a href="#">고객센터 문의하기</a></p>
	</div>
</body>
</html>