<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   	<h2>attrScopeResult.jsp</h2>

	${pageScope.id}<br>
	${requestScope.id}<br>
	${sessionScope.id}<br>
	${applicationScope.id}<br>
	
	<a href="#">새로고침</a>