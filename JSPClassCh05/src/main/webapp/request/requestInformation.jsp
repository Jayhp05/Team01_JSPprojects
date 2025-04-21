<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1><%= request.getMethod() %> 방식 요청 정보</h1>
	<h2>서버 정보</h2>
	<ul>
		<li>서버 이름 : <%= request.getServerName() %></li>
		<li>서버 포트 : <%= request.getServerPort() %></li>
	</ul>
	
	<h2>요청 정보</h2>
	<ul>
		<li>요청 방식 : <%= request.getMethod() %></li>
		<li>프로토콜 : <%= request.getProtocol() %></li>
		<li>요청 URL : <%= request.getRequestURL() %></li>
		<li>요청 URI : <%= request.getRequestURI() %></li>
		<li>컨텍스트 경로 : <%= request.getContextPath() %></li>
		<li>컨텐트 타입 : <%= request.getContentType() %></li>
		<li>컨텐트 길이 : <%= request.getContentLength() %></li>
	</ul>
	
	<h2>클라이언트 정보</h2>
	<ol>
		<li>클라이언트 이름 : <%= request.getRemoteHost() %></li>
		<li>클라이언트 포트 : <%= request.getRemotePort() %></li>
		<li>클라이언트 주소 : <%= request .getRemoteAddr() %></li>
		<li>클라이언트 유저 : <%= request.getRemoteUser() %></li>
		<li>웹 브라우저와 클라이언트의 시스템 정보 : <br>
			<%= request.getHeader("User-Agent") %></li>
		<li>브라우저 지원 File Type : <%= request.getHeader("Accept") %></li>
		<li>바로 이전 URL : <%= request.getHeader("referer") %></li>
	</ol>
</body>
</html>