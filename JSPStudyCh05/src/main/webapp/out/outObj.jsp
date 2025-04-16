<%-- out 내장객체를 이용해 응답 데이터 출력하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="4kb" autoFlush="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	 // out 객체는 스트림을 통해서 브라우저로 출력을 전담하는 내장객체 이다.
	out.println("<h2>out 내장객체</h2>");
	out.println("<div>div 요소 안의 내용</div>");
%>
	<!--
		아래와 같이 JSP 페이지에서 정적인 텍스트 데이터를 기술하면 이 JSP 페이지가
		서블릿 클래스로 변환될 때 out 내장객체를 이용해 출력하는 코드로 변환된다.
	-->
	<div>여기도 out 내장객체를 이용해 출력된다.</div>
</body>
</html>
