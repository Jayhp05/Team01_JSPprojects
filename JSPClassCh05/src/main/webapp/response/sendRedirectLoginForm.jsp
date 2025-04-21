<%-- response 내장객체의 sendRedirect()를 이용한 초간단 로그인 처리 폼 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
<title>로그인 화면</title>
</head>
<body>
	<form action="sendRedirectLoginProcess.jsp" method="post">
		아이디: <input type="text" name="id" /><br/>
		비밀번호 : <input type="password" name="pass" /><br/>
		<input type="submit" value="로그인" />
	</form>
</body>
</html>