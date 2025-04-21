<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>내장객체 속성의 유효범위</title>
</head>
<body>
	<h2>아이디와 이름 입력</h2>
	<form action="attrScopeProcess.jsp" method="post">
		아이디: <input type="text" name="id" /><br/>
		이름 : <input type="text" name="name" /><br/>
		<input type="submit" value="등록하기" />
	</form>
</body>
</html>