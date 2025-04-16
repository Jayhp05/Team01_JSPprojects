<%-- 세션 정보 출력하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>    
<%
	// 세션 유효시간을 60초로 설정	
	session.setMaxInactiveInterval(60);
	Calendar ca = Calendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션 정보 출력하기</title>
</head>
<body>
	<!-- 맨 처음 한 번은 새로운 세션이므로 true, 새로 고침 하면 false 이다. -->
	새로운 세션 여부 : <%= session.isNew() %><br/>
	
	<!-- 세션 ID는 서버에서 유일한 세션 ID 값을 갖는다. -->
	세션 ID : <%= session.getId() %><br/>
<%
	ca.setTimeInMillis(session.getCreationTime());	
%>	
	세션 생성 시간 : <%= String.format(
			"%TY년 %Tm월 %Td일 %TT", ca, ca, ca, ca) %><br/>
<%
	ca.setTimeInMillis(session.getLastAccessedTime());
%>
	<!-- 브라우저를 새로 고침 할 때 마다 마지막 접근 시간은 바뀐다. -->
	마지막 접근 시간 : <%= formatter.format(ca.getTime()) %><br/>
	세션 유효 시간 : <%= session.getMaxInactiveInterval() %><br/>
</body>
</html>