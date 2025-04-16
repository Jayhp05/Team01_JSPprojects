<%-- page 지시자와 JSP 주석 --%>
<%@ page language="java"  contentType="text/html; charset=utf-8" 
	pageEncoding="utf-8" %>
    <%--   
    	여기는 JSP에서 지원하는 주석이다.
    	page 지시자는 현재 JSP 페이지에서 필요한 설정 정보나 클래스의 임포트 등에
    	사용할 수 있다. 위의 page 지시자에서 contentType은 브라우저로 전송할 
    	응답 결과에 대한 문서의 형식과 문자 셋을 지정하는 속성으로 브라우저는 이 속성에
    	지정된 문서형식과 문자 셋을 기준으로 문서를 해석하여 화면에 출력하게 된다. 
    	문서 형식을 MIME(Multipurpose Internet Mail Extensions) 타입이라고
    	하며 이메일의 내용을 설명하기 위해 정의되었으나 오늘날 메일 뿐만 아니라
    	HTTP 등의 프로토콜을 이용해 전송되는 문서의 내용을 설명하기 위해 사용하고 있다.
    	MIME 타입을 생략하게 되면 기본값은 "text/html"이며 charset을 생략하면
    	기본값은 톰캣의 기본 문자 셋인 "iso-8859-1"을 사용하게 된다.
    	
    	pageEncoding은 이 문서의 인코딩 방식을 지정하는 속성으로 서블릿 컨테이너가
    	JSP 페이지를 분석할 때 JSP 페이지가 어떤 문자셋으로 작성되었는지 검사하고
    	그 문자셋을 이용해 JSP 페이지를 읽어 해석하게 된다. 
    	
    	서블릿 컨테이너는 pageEncoding 속성을 먼저 검색하고 contentType 속성의 
    	charset의 값을 검색하게 되는데 만약 pageEncoding 속성을 지정하지 않고
    	conatenType 속성의 charset에 문자 셋을 잘못 지정하게 되면 한글과 같은
    	유니코드 문자가 깨지는 현상이 나타나게 된다.
    --%>
 <%
 	int sum = 0; 	 	
 	
 	/* 1부터 100까지 반복문 - 자바의 여러줄 주석
 	 * 스크립틀릿(Scriptlet)에서 자바의 한 줄 주석과 여러 줄 주석을 사용할 수 있다.
 	 **/
 	for(int i = 1; i <= 100; i++) { 		
 		// sum 변수에 i의 값을 누적하여 더해 1~100까지 합을 구한다.
 		sum += i; 		
 	}
 %>
<!DOCTYPE html>
<html>
<!-- 여기부터 HTML Head - HTML 주석 -->
<head>
<title>JSP의 pageEncoding 속성</title>
</head>
<!-- HTML 본문 시작 -->
<body>
	<%-- 
		JSP 주석은 스크립팅요소 안에서는 사용할 수 없고 HTML 태그 사이에서 사용할 수 있다.
		위쪽의 스크립틀릿에서 계산한 결과를 출력
	--%>
	1 ~ 100까지의 합 : <%= sum %><br/>
</body>
</html>
