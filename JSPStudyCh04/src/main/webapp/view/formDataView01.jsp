<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 기본 정보</title>
</head>
<body>
	<h2>회원 기본 정보</h2>
	<!-- 서블릿 3.0부터 EL에서 자바 클래스의 메서드를 사용할 수 있다. -->
	이 름 : ${ name.equals("") ? "null" : name }<br/>
	아이디 : ${ id }<br/>
	비밀번호 : ${ pass }<br/>
	성 별 : ${ gender }<br/>
	공지메일 : ${ nMail }<br/>
	광고메일 : ${ aMail }<br/>
	정보메일 : ${ iMail }<br/>
	직 업 : ${ job }
</body>
</html>
