<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%-- 
	<c:set /> 태그는 scope에 지정한 내장객체에 속성을 생성하고 속성값을 
	지정하는 데 사용하는 태그로 이 태그의 scope 속성에	지정할 수 있는 값은
	속성에 데이터를 저장할 수 있는 page, request, session, application이 있다.
	scope 속성을 지정하지 않으면 기본적으로 page가 적용된다. 
	
	아래는 application 내장객체에 ADMIN_ID라는 속성 이름으로 admin 이라는 
	값을 저장하게 된다. 즉 application.setAttribute("ADMIN_ID", "admin");과
	같은 역할을 수행한다.
  --%>
<c:set var="ADMIN_ID" value="admin" scope="application" />
<c:set var="ADMIN_PASS" value="1234" scope="application" />

<%-- 
	EL의 param 내장 객체를 이용해 로그인 폼에서 파라미터로 넘어온 id와 pass를 읽어
	위에서 <c:set /> 태그로 application 영역에 속성으로 저장한 데이터와
	비교해 맞으면 isLogin 변수에 true를 설정해 session 영역에 저장한다.
	또한 로그인에 성공한 사용자 아이디도 session 영역에 저장했다.
	
	EL을 사용하면 속성에 데이터를 저장할 수 있는 JSP의 내장객체인 pageContext,
	request, session, application 영역을 순서대로 검색하여 첫 번째 만나는
	속성에 해당하는 데이터를 읽어온다. EL 자체에도 JSP의 각 내장객체에 대응하는
	EL의 내장객체를 아래와 같이 지원하고 있다. 
	
	JSP 내장객체		->		EL 내장객체 
	pageContext 	->		pageScope
	request			->		requestScope
	session			->		sessionScope
	application		->		applicationScope
	
	EL의 내장객체를 이용해 아래와 같이 해당 영역에서 데이터를 바로 읽어 올 수 있다.	
  --%>
<c:if test="${ param.id == applicationScope.ADMIN_ID
	&& param.pass == applicationScope.ADMIN_PASS }" >
	<c:set var="isLogin" value="true" scope="session" />
	<c:set var="id" value="${ param.id }" scope="session" />
	<!-- 
		jstl이 제공하는 태그를 이용해 리다이렉트 하고 있다.
		response.sendRedirect()를 호출한 결과와 동일하다. 
	-->	 
	<c:redirect url="main.jsp" />
</c:if>
<c:if test="${ not (param.id == applicationScope.ADMIN_ID
	&& param.pass == applicationScope.ADMIN_PASS) }" >
	<script>
		alert("아이디 또는 비밀번호가 맞지 않습니다.");
		history.back();
	</script>
</c:if>	


