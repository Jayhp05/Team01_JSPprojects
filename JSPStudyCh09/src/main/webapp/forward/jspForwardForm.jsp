<%-- <jsp:forward> 액션태그를 이용해 포워딩하기 - 폼 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원 가입</h1>
	<form action="jspForwardProcess.jsp">
		이 름 : <input type="text" name="name" /><br/>
		나 이 : <input type="text" name="age" /><br/>
		성 별 : <input type="radio" name="gender" value="남성" />남성
			<input type="radio" name="gender" value="여성" />여성<br/>				
		<input type="reset" value="다시쓰기" />
		<input type="submit" value="전송하기" />
	</form>
</body>
</html>