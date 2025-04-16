<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>attrScopeResult.jsp</title>
</head>
<body>
<body>
	<h2>attrScopeResult.jsp</h2>
	<%-- 
		EL를 사용해 속성 이름을 지정하면 pageContext, request, session, 
		application 4개의 영역에 저장된 속성을 작은 범위에서 큰 범위 순으로 
		검색하여 지정한 이름의 속성에 대한 값을 얻어 올 수 있다. 속성이 존재하지 
		않아도 NullPointerException은 발생하지 않고 아무것도 출력되지 않는다.
		
		EL을 사용하면 속성에 데이터를 저장할 수 있는 JSP의 내장객체인 pageContext,
		request, session, application 영역을 순서대로 검색하여 첫 번째 만나는
		속성에 해당하는 데이터를 읽어온다. EL 자체에도 JSP의 각 내장객체에 대응하는
		EL의 내장객체를 아래와 같이 지원하고 있다. 
	
		JSP 내장객체		->		EL 내장객체 
		pageContext	->		pageScope
		request			->		requestScope
		session			->		sessionScope
		application		->		applicationScope
		
		아래와 같이 EL 안에서 데이터를 읽어 올 영역(scope)을 지정하면 검색하지 않고
		해당하는 영역에서 데이터를 읽어 온다. 해당하는 영역에 지정한 데이터가 존재하지
		않으면 아무것도 출력되지 않는다.  
	--%>
	
	<%--
		attrScopeProcess.jsp에서 pageContext 내장객체의 속성에 저장한
		데이터는 유효범위를 벗어나기 때문에 아래에서 아무것도 출력되지 않는다.	  
	--%>		
	page : ${ pageScope.id }<br/>
	
	<%--
		attrScopeProcess.jsp에서 포워딩하여 이 페이지로 제어가 이동해 왔다면 
		request 내장객체의 속성에 저장한 데이터는 유효하기 때문에 id가 출력된다.
		하지만 attrScopeProcess.jsp 페이지에서 링크를 클릭해서 이 페이지로
		넘어왔다면 id는 출력되지 않는다. 이유는 attrScopeProcess.jsp 페이지가
		브라우저 화면에 출력될 때 이미 서버에서 응답이 완료되었기 때문에 그 때 서버에서
		사용한 request 내장객체는 사라져서 존재하지 않으므로 유효범위를 벗어나게 된다.		
		결과가 브라우저 화면에 출력된 후 링크를 클릭하여 서버로 요청하면 새로운 요청이 
		발생하기 때문에 이전의 request 내장객체는 더 이상 유효하지 않게 된다.
	--%>
	
	request : ${ requestScope.id }<br/>
	
	<%--
		session과 application 내장객체는 여러 번의 요청에서도 유효하다.
		사용한 브라우저 화면이 모두 닫히면 세션이 종료되어 현재 session 내장객체의
		유효범위를 벗어나게 되고 현재 웹 애플리케이션이 다시 시작되면 이전의 application
		내장객체는 소멸되고 다시 생성되기 때문에 현재의 application 내장객체의
		유효범위를 벗어나게 된다.     
	--%>
	session : ${ sessionScope.id }<br/>
	application : ${ applicationScope.id }<br/><br/>
	<a href="#">새로고침</a>
</body>
</html>