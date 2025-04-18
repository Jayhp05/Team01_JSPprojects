<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String name = (String) request.getAttribute("name");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원 기본 정보</h2>
	여기다 회원 정보 출력<br>
	<%-- 이름 : <%= name %> --%>
	이름 : ${name}<br>
	아이디 : ${id}<br>
	비밀번호 : ${pass}<br>
	성 별 : ${gender}<br>
	공지메일 : ${iMail}<br>
	광고메일 : ${aMail}<br>
	정보메일 : ${nMail}<br>
	직업 : ${job}<br>
</body>
</html>