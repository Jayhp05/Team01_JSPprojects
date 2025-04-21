<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
	내장객체 데이터를 저장할 수 있는 속성을 제공하는 4개의 내장객체
	[내장객체 - 클래스이름]
	
	pageContext - pageContext
	request - HttpServletRequest
	session - HttpSession
	application - ServletContext
	
	application 내장객체
 -->

	<h2>웹 서버 정보</h2>
	<ul>
		<li>웹 서버 종류 : <%= application.getServerInfo() %></li>
		<li>서블릿 버전 : <%= application.getMajorVersion() %>.
			<%= application.getMinorVersion() %></li>
	</ul>
	
	<h2>웹 애플리케이션 정보</h2>
	<ul>
		<li>웹 애플리케이션 켄텍스트 경로 : <%= application.getContextPath() %></li>
		<li>웹 애플리케이션 파일의 절대경로 : <%= application.getRealPath("applicationMethods.jsp") %></li>
		<li>웹 애플리케이션 이름 : <%= application.getServletContextName() %></li>
		<li>세션 설정 시간 : <%= application.getSessionTimeout() %></li>
	</ul>
	
	<%
		application.setAttribute("test01", "application에서 저장함.");
	%>
	
</body>
</html>