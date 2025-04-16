<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	/* invalidate() 메서드는 현재 세션을 종료하는 메서드로 이 메서드가 호출되면	 
 	 * 기존의 세션을 종료하고 새로운 세션이 시작되기 때문에 이전 세션 영역에
 	 * 저장된 모든 데이터가 삭제된다. 
 	 **/
	session.invalidate();
	
	/*
	// 아래와 같이 세션에 저장된 개별 속성만 삭제 할 수 있다.
	session.removeAttribute("isLogin");
	session.removeAttribute("id");
	*/
%>
<%--     
	아래와 같이 jstl을 사용해서 세션에 저장된 개별 속성만 삭제할 수 있다.
	<c:remove var="isLogin" scope="session" />
	<c:remove var="id" scope="session" />
 --%>
<!-- 
	jstl이 제공하는 태그를 이용해 리다이렉트 하고 있다.
	response.sendRedirect()를 호출한 결과와 동일하다. 
--> 
<c:redirect url="main.jsp" />
