<%-- <jsp:forward> 액션태그를 이용해 포워딩하기 - 요청 처리 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// <jsp:param> 태그에 대한 문자 셋 처리
	request.setCharacterEncoding("utf-8");
	
	// 폼에서 전송된 파라미터 읽기
	String name = request.getParameter("name");
	String age = request.getParameter("age");
	String gender = request.getParameter("gender");
%>
<%-- 
	<jsp:forward> 표준 액션 태그는 요청 처리를 다른 JSP 페이지로 넘길 때 
	사용한다. 이때 이동하는 페이지로 파라미터를 보내려면 <jsp:param> 태그를
	사용해 파라미터를 전달할 수 있다. <jsp:param> 태그는 독립적으로 사용할 수
	없고 <jsp:forward> 태그의 자식 태그로 사용할 수 있다. 또한 <jsp:param>
	태그로 전달되는 파라미터는 request 내장객체의 setCharacterEncoding()
	메소드에 지정한 문자 셋을 사용해 파라미터를 인코딩하므로 파라미터에 한글이 
	포함되는 경우 <jsp:param> 태그를 사용하기 이전에 setCharacterEncoding()
	메소드를 사용해 적절한 문자 셋을 지정해야 한글 데이터가 깨지지 않는다.
	
	forward 기법은 요청 처리와 화면 구현을 분리해서 처리하기 위해 서버에서 요청을
	처리하는 과정에서 요청 처리가 완료되면 제어를 화면 구현하는 쪽으로 이동해 요청을
	처리한 결과 데이터를 출력하여 최종적으로 응답하기 위해 사용하는 기법이다.     
	
	forward로 이동하는 페이지에 아래와 같이 파라미터로 데이터를 보내는 것 보다
	request.setAttribute() 메서드를 이용해 request 영역의 속성에 데이터를
	저장하여 View 페이지에서 사용할 수 있도록 하는 것이 EL을 사용해 데이터에 바로
	접근할 수 있기 때문에 더 간결하고 효율적이다.
--%>
<jsp:forward page="jspForwardResult.jsp">
	<jsp:param name="name" value="<%= name %>" />
	<jsp:param name="age" value="<%= age %>" />
	<jsp:param name="gender" value='<%= gender %>' />
</jsp:forward>

