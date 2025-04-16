<%-- <jsp:forward> 액션태그를 이용해 포워딩하기 - 최종 결과(뷰) 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보</h1>
	<%-- EL의 param 내장객체를 이용해 요청 파라미터를 읽을 수 있다. --%>
	이름 : ${ param.name }<br/>
	나이 : ${ param.age }<br/>
	성별 : ${ param.gender }<br/>	
</body>
</html>
