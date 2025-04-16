<%-- request 내장객체를 이용한 클라이언트와 서버정보 출력하기 폼 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GET 방식과 POST 방식 요청 정보 보기</title>
</head>
<body>
	<%-- 링크는 GET 방식 요청 --%>
	<a href="requestInformation.jsp?num1=100&num2=200">요청정보보기</a><br/><br/>	
	<%-- POST 방식 요청 --%>
	<form action="requestInformation.jsp" method="post">
		이름 : <input type="text" name="name" /><br/>
		비밀번호 : <input type="text" name="pass" /><br/>
		<input type="submit" value="전송" />
	</form>	
</body>
</html>