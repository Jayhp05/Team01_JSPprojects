<%-- 스크립팅 요소(Scripting Element) 사용하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	이 부분은 JSP의 주석문 이다.
	선언부에는 클래스 멤버와 인스턴스 멤버를 선언할 수 있다. 
	JSP 페이지의 선언부에 선언된 변수와 메서드는 서블릿으로 변환된 클래스의
	멤버가 되므로 접근지정자, final, static 등의 예약어를 사용할 수 있다. 
	jsp의 선언부는 요즘에는 거의 사용하지 않는다.
--%>    
<%!	
	private int add(int a, int b) {		
		return a + b;
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스크립팅 요소(Scripting Element) 사용하기</title>
</head>
<body>
	<%--
		JSP 페이지에서 앞쪽의 스크립틀릿에 선언된 변수는
		뒤쪽의 여러 스크립틀릿과 표현식에서 사용할 수 있다.		 
	--%>
	<%
		int sum = 0;
		for(int i = 1; i <= 100; i++) {
			sum += i;
		}
	%>
	
	<%-- 표현식 안의 변수나 자바 코드는 결과를 출력하는 서블릿 코드로 변환된다. --%>
	1 ~ 100 합 : <%= sum %><br/>
	
	<%
		for(int i = 1; i <= 10; i++) {
	%>
		<%-- 위의 스크립틀릿의 for 문에서 정의한 i를 출력 --%>
		<%= i %>번<br/>
	<%
		}
		// 스크립틀릿에서의 주석은 자바 주석을 그대로 사용하면 된다.
		// 위쪽의 스크립틀릿에서 선언한 sum에 0을 대입한다.
		sum = 0;
	%>	
	sum : <%= sum %><br/>
	
	<%-- 위쪽의 선언부에 선언한 메서드를 호출하여 덧셈 결과를 출력 --%>
	10 + 20 = <%= add(10, 20) %>
</body>
</html>