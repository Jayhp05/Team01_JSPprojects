<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- id : admin, pass : 1234 -->
<!-- jstl core if, for, 제어문, 변수 선언, 설정 -->
<%
//	db X
	/* application.setAttribute("ADMIN_ID", "admin"); -- scope가 application인 경우 */
	/* session.setAttribute("ADMIN_ID", "admin"); -- scope가 session인 경우 */
%>

<c:set var="ADMIN_ID" value="admin" scope="application" />
<%-- <c:set var="ADMIN_ID" value="admin" scope="session" /> --%>
<c:set var="ADMIN_PASS" value="1234" scope="application" />

<!-- 로그인 성공 - main.jsp로 리다이렉트 -->
<c:if test="${param.id == applicationScope.ADMIN_ID
			&& param.pass == applicationScope.ADMIN_PASS}">
			
	<c:set var="isLogin" value="true" scope="session"/>
	<c:set var="id" value="${param.id}" scope="session"/>
	
	<c:redirect url="main.jsp"/>
</c:if>

<!-- 로그인 실패 - 자바스크립트로 응답 -->
<c:if test="${not (param.id == applicationScope.ADMIN_ID
			&& param.pass == applicaionScope.ADMIN_PASS)}">

	<script>
		alert("아이디 또는 비밀번호가 맞지 않습니다.");
		history.back();
	</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>