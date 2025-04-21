<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	
	/* 데이터를 저장할 수 있는 속성을 제공하는 4개의 내장객체 */
	/* pageContext - request - session - application */
	/* 현재 페이지 안에서 유효 */
	pageContext.setAttribute("id", id + " - pageContext");
	
	/* 한 번의 요청 사이클 안에서 유효 */
	request.setAttribute("id", id + " - request");
	
	/* 하나의 브라우저(한 명의 접속) 접속 안에서 유효 */
	session.setAttribute("id", id + " - session");
	
	/* 하나의 웹 애플리케이션 범위 안에서 유효 */
	application.setAttribute("id", id + " - application");
	
	// pageContext.forward("attrScopeResult.jsp"); // forward메서드는 보안성 뛰어나 서버의 바깥에서는 어떤 요청을 했는지 알 수 없음.
%>

	<h2>attrScopeProcess.jsp</h2>

	${pageScope.id}<br>
	${requestScope.id}<br>
	${sessionScope.id}<br>
	${applicationScope.id}<br>
	
	<a href="attrScopeResult.jsp">다음으로</a>