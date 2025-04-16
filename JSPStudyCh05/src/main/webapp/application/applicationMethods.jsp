<%-- application 내장객체의 메서드 사용하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%--
		applicaton 내장객체는 하나의 웹 애플리케이션 정보를 저장하고 관리하기 위해
		사용하는 객체로 ServletContext 타입이다. application 내장객체는 하나의
		웹 애플리케이션 안에서 유효하며 웹 애플리케이션을 사용하는 모든 사용자와 관련해 
		파일을 업로드 하는 서버내의 폴더 정보나 웹 애플리케이션의 설정정보 등의 필요한 
		정보를 웹 컴포넌트(JSP, Servlet) 간의 공유하기 위해 사용되며 주로 서버와 
		웹 애플리케이션 정보에 접근할 수 있는 메서드를 제공하고 있다.
		  
		아래는 application 내장 객체를 이용해 서버 정보와 서블릿 정보를 출력하고 있다. 
	--%>
	<h3>웹 서버 정보</h3>
	<ul>
		<li>웹 서버 종류 : <%= application.getServerInfo() %></li>
		<li>서블릿 버전 : <%= application.getMajorVersion() %>.
			<%= application.getMinorVersion() %></li>
	</ul>
	<h3>웹 애플리케이션 정보</h3>
	<ul>
		<li>웹 애플리케이션 컨텍스트 패스 : <%= application.getContextPath() %></li>
		<li>웹 애플리케이션 파일의 절대경로 : 
			<%= application.getRealPath("applicationMethods.jsp") %></li>
		<li>웹 애플리케이션 이름 : <%= application.getServletContextName() %></li>		
	</ul>
</body>
</html>