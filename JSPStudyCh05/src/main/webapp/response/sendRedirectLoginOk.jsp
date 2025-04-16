<%-- 정상적인 로그인이면 Redirect되어 최종적으로 화면에 출력되는 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 성공</title>
</head>
<body>
	<h2> 안녕하세요 <%= request.getParameter("id") %> 님!</h2>
</body>
</html>