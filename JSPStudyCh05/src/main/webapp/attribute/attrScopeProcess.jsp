<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	/* POST 방식 요청인 경우 setCharacterEncoding("UTF-8")을 호출해
	 * 웹 브라우저의 인코딩 방식과 동일한 문자 셋인 UTF-8을 지정해야 
	 * 한글 데이터와 같은 유니코드 문자를 깨지지 않게 처리할 수 있다.
	 * 
	 * getParameter() 메서드가 호출되기 전에 setCharacterEncoding("UTF-8")
	 * 메서드를 먼저 호출해 request 영역의 문자 셋을 먼저 처리해야 한다.  
	 */
	request.setCharacterEncoding("utf-8");
	
	// request 내장객체를 이용해 요청 파라미터를 읽어온다. 
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	
	/* 데이터를 저장할 수 있는 속성을 제공하는 4가지 내장객체에 id를 저장한다.
	 * 속성을 제공하는 내장객체에 저장할 수 있는 데이터는 기본형과 참조형 모두 가능하다.
	 * 아래와 같이 setAttribute() 메서드를 이용해 속성에 데이터를 저장할 수 있고
	 * getAttribute() 메서드와 EL을 이용해 속성의 데이터를 읽어 올 수 있다.
	 **/
	 
	/* pageContext 내장객체의 속성은 한 번의 요청을 처리하는 같은 JSP 페이지 내에서
	 * 데이터를 공유하기 위해서 사용되며 주로 같은 JSP 내에서 스크립틀릿과 
	 * EL(Expression Language) 간의 데이터를 교환할 때 사용된다.
	 **/
	pageContext.setAttribute("id", id);
	
	/* request 내장객체의 속성은 한 번의 요청을 처리하는 서블릿과 JSP 페이지 간에
	 * 데이터를 공유하기 위해 사용되며 RequestDispatcher나 pageContext 객체의
	 * forward() 메소드를 이용해 요청 제어를 다른 페이지로 넘길 때 사용한다.
	 * forward 되는 JSP 페이지의 스크립틀릿이나 표현식에서 getAttribute() 메소드로
	 * 속성의 데이터를 읽어 올 수 있으며 EL을 이용해도 속성의 데이터를 읽어 올 수 있다.
	 **/  
	request.setAttribute("id", id);
	
	/* session은 하나의 브라우저 접속(한 명의 사용자 접속을 의미하며 이를 세션이라고 함) 
	 * 안에서 유효한데 주로 한 사용자(같은 세션)와 관련된 정보(로그인 정보, 장바구니 등등)를
	 * 여러 JSP 페이지(웹 컴포넌트)가 공유하기 위해서 사용한다.
	 **/
	session.setAttribute("id", id);
	
	/* applicaton 내장객체는 하나의 웹 애플리케이션 정보를 저장하고 관리하기 위해
	 * 사용하는 객체로 ServletContext 타입이다. application 내장객체는 하나의
	 * 웹 애플리케이션 안에서 유효하며 웹 애플리케이션을 사용하는 모든 사용자와 관련해 
	 * 파일을 업로드 하는 서버내의 폴더 정보나 웹 애플리케이션의 설정정보 등의 필요한 
	 * 정보를 웹 컴포넌트(JSP, Servlet) 간의 공유하기 위해 사용되며 주로 서버와 
	 * 웹 애플리케이션 정보에 접근할 수 있는 메서드를 제공하고 있다.
	 **/	
	application.setAttribute("id", id);
		
	/* pageContext 내장객체를 이용해 다른 JSP 페이지로 포워딩 한다. 
	 **/
	 // pageContext.forward("attrScopeResult.jsp");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>attrScopeProcess.jsp</title>
</head>
<body>
	<h2>attrScopeProcess.jsp</h2>
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
	page : ${ pageScope.id }<br/>
	request : ${ requestScope.id }<br/>
	session : ${ sessionScope.id }<br/>
	application : ${ applicationScope.id }<br/><br/>
	<a href="attrScopeResult.jsp">attrScopeResult.jsp</a>
</body>
</html>