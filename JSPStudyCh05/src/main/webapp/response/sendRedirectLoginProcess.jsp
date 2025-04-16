<%-- response 내장객체의 sendRedirect()를 이용한 초간단 로그인 처리 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");	
	
	/* 아이디와 비밀번호가 맞으면 response 내장객체의 sendRedirect() 메서드를 이용해 
	 * 로그인 성공 페이지로 리다이렉트(Redirect) 시킨다. sendRedirect() 메서드는
	 * 요청한 자원이 다른 곳으로 이동되었다고 브라우저에게 응답하면서 이동할 URL을 
	 * 알려주고 그 쪽으로 다시 요청하라고 응답하는 메서드이다. Redirect 기법은 웹
	 * 브라우저를 새로 고침(F5) 했을 때 동일한 코드가 재실행되면 문제가 될 수 있는
	 * 경우에 클라이언트의 요청을 모두 처리한 후 특정 URL로 이동시키기 위해 주로
	 * 사용한다. 예를 들어 게시 글쓰기에 대한 요청을 처리한 후에 Redirect 시키지
	 * 않는다면 브라우저의 주소 표시줄에 게시 글쓰기에 대한 URL이 그대로 남아 있기
	 * 때문에 사용자가 브라우저를 새로 고침(F5) 하게 되면 바로 이전에 실행된 게시
	 * 글쓰기 작업이 반복 실행되어 중복된 데이터가 저장되는 문제가 발행할 수 있다. 
	 * 이를 방지하기 위해서 게시 글쓰기가 완료되면 게시 글 리스트로 이동시키기
	 * 위해서 response 내장객체의 sendRedirect() 메서드를 사용해 게시 글
	 * 리스트의 URL을 웹 브라우저에게 응답하고 웹 브라우저는 응답 받은 URL로
	 * 다시 요청하도록 하는 것이다. 왜 게시 글 리스트로 리다이렉트 시켜야 하는가?
	 * 게시 글 리스트는 DB에서 데이터를 조회하는 쿼리인 SELECT 쿼리를 사용하기
	 * 때문에 이 쿼리가 실행되어도 데이터가 중복되어 저장되거나, 동일한 데이터를
	 * 반복적으로 수정 또는 삭제하려는 문제가 발생되지 않기 때문이다. 이렇게 게시
	 * 글쓰기와 같이 DB에서 데이터의 입력, 수정, 삭제 작업과 연동되는 경우에	 
	 * 사용자의 새로 고침(F5) 등으로 문제가 발생할 수 있기 때문에 Redirect를
	 * 사용한다. 이외에 다른 사이트로 이동시킬 때에도 Redirect 기법을 사용 한다.
	 **/
	if(id.equals("admin") && pass.equals("1234")) {
		response.sendRedirect("sendRedirectLoginOk.jsp?id=" + id);
		
	} else {	
%>
<!DOCTYPE html>
<html>
<head>
<title>Redirect</title>
<script type="text/javascript">
	alert("아이디나 패스워드가 맞지 않습니다.");
	document.location.href("sendRedirectLoginForm.jsp");
</script>   
<%
	}
%>
</head>
<body><h2>로그인 실패</h2></body>
</html>