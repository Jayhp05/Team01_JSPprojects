<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	session.setMaxInactiveInterval(60);
	Calendar ca = Calendar.getInstance();
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	세션이 새로운 세션 인가? : <%= session.isNew() %><br>
	세션 id : <%= session.getId() %><br>
	<%
		ca.setTimeInMillis(session.getCreationTime());
	%>
	세션 생성 시간 : <%= String.format("%TY년 %Tm월 %Td일 %TT", ca, ca, ca, ca) %><br>
	<%
		ca.setTimeInMillis(session.getLastAccessedTime());
	%>
	마지막 접근 시간 : <%= formatter.format(ca.getTime()) %><br>
	세션 유효 시간 : <%= session.getMaxInactiveInterval() %><br>
</body>
</html>