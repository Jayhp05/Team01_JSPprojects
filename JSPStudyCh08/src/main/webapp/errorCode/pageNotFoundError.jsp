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
<title>PageNotFound 에러 처리</title>
<style>
	p { font-size: 12px; }
</style>
</head>
<body>
	<h2>페이지 없음</h2>
	<div>
		<p>요청하신 페이지를 찾을 수 없습니다.</p>
		<p>방문하려는 페이지의 주소가 잘못 입력되었거나,<br/>
			페이지가 변경되거나 삭제되어 요청하신 페이지를 찾을 수 없습니다.</p>		
		<p><a href="#">고객센터 문의하기</a></p>
	</div>
</body>
</html>