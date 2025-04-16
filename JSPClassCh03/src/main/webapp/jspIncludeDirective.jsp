<%-- include 지시자 사용하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date, java.util.Calendar" %>
<%	
	Calendar ca = Calendar.getInstance();
	Date now = ca.getTime();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>다른 JSP 페이지 포함하기</title>
</head>
<body>
	<h2>◈ 오늘의 메뉴 ◈</h2>	
	- 명태조림  10,000원<br/>
	- 버섯 매운탕  10,000원<br/>
	- 불고기 정식  11,000원<br/><br/>
	
	<%-- 
		include 지시자를 사용해 컴파일 타임에 다른 JSP 페이지의 내용을
		현재 JSP 페이지에 포함할 수 있다. 이렇게 include 지시자를 사용하면
		JSP 페이지가 톰캣 서버에 의해서 최초로 실행될 때 현재 JSP 페이지
		(부모 페이지라고 함)에 아래에 include 지시자로 지정한 JSP 페이지
		(자식 페이지라고 함)가 하나로 합쳐져서 Servlet 클래스로 변환되고
		컴파일 되어 하나의 서블릿으로 동작하게 된다.
	--%>
	<%@ include file="includeMenu.jsp" %>
	
	<%-- java.lang 패키지는 기본 패키지로 import 없이 사용 가능하다. --%>
	<b><%= String.format("%1$TY년 %1$Tm월 %1$Td일", now) %></b>
</body>
</html>