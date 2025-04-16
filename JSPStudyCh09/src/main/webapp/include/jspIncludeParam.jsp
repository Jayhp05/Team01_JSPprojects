<%-- <jap:include> 액션태그를 이용해 다른 JSP페이지 포함하기 - 부모 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// <jsp:param> 태그에 대한 문자 셋 처리
	request.setCharacterEncoding("utf-8");
	
	String book = "백견불여일타 JSP&Servlet";
	String image = "images/jsp&servlet.jpg";	
	int price = 27000;
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="font-size: 12px;">
	<h2>도서 정보 보기</h2>
	<%-- 
		<jsp:include> 표준 액션 태그를 사용해 다른 JSP 페이지를 실행 타임에
		include 하고 <jsp:param> 표준 액션 태그를 사용해 포함될 JSP 페이지에 
		파라미터를 전달할 수 있다. <jsp:param> 태그는 독립적으로 사용할 수 없고
		<jsp:include> 태그의 자식 태그로 사용할 수 있다. 또한 <jsp:param>
		태그로 전달되는 파라미터는 request 내장객체의 setCharacterEncoding()
		메소드에 지정한 문자 셋을 사용해 파라미터를 인코딩하므로 파라미터에 한글이 
		포함되는 경우 <jsp:param> 태그를 사용하기 이전에 setCharacterEncoding()
		메소드를 사용해 적절한 문자 셋을 지정해야 한글 데이터가 깨지지 않는다.
		
		include 기법은 다른 페이지를 현재 페이지에 포함 시키는 기능으로 여러 
		페이지를 하나의 페이지 처럼 묶어서 동작시킬 때 유용하게 사용할 수 있다.
		일반적인 웹 페이지는 헤더, 푸터, 컨텐츠 부분으로 나누어지는데 헤더와 푸터는
		거의 대부분 내용이 동일하기 때문 매 페이지의 상단과 하단이 중복된다.
		이럴 때 각각 헤더, 푸터, 컨텐츠를 분리해서 따로 작성하고 실제로 웹 페이지가
		실행될 때 하나로 합쳐져서 실행되도록 구현하면 최종적으로 하나의 완성된 HTML
		문서만 클라이언트에 응답되기 때문에 여러 페이지에 중복될 수 있는 코드의
		중복을 최소화 시킬 수 있는 보다 효율적인 웹 페이지 제작 기법이라 할 수 있다. 
	--%>	
	<jsp:include page="jspIncludeParamSub.jsp" >		
		<jsp:param name="book" value="<%= book %>" />
		<jsp:param name="image" value="<%= image %>" />
		<jsp:param name="price" value="<%= price %>" />		
	</jsp:include>	
</body>
</html>