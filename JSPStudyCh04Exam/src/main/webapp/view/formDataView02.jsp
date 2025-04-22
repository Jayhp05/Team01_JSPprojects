<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String name = (String) request.getAttribute("name");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>학생 등록 정보</h2>
	<%-- 이름 : <%= name %> --%>
	이 름 : ${name}<br>
	성 별 : ${gender}<br>
	연락처 : ${call}<br>
	희망 취업 분야 : ${si, sm, solution}<br>
	관심분야 : ${star}<br>
</body>
</html>