<%-- 
	<%@ %> -> 지시자를 뜻함 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.util.*" %>

<% 	/* 자바 코드가 기재되는(작성되는) 스트립틀릿 scriptlet */
	/* 날짜 데이터를 다루기 위해서 Calendar 객체를 생성
	 * 아래는 시스템의 현재 날짜와 시간 정보를 가진 Calendar 객체가 생성된다.
	 **/
	Calendar today = Calendar.getInstance();
%>    
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<h1>오늘의 날짜</h1>
	오늘은 <%= today.get(Calendar.YEAR) %>년 
		<%= today.get(Calendar.MONTH) + 1 %>월
		<%= today.get(Calendar.DAY_OF_MONTH) %>일 입니다.<br/>
		현재 시간 : <%= today.get(Calendar.HOUR) %> :
				<%= today.get(Calendar.MINUTE) %> :
				<%= today.get(Calendar.SECOND) %>	
</body>
</html>