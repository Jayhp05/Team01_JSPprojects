<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--
	로그인 id=admin, pass=1234 맞으면
	로그인 성공 처리 => sendRedirectLoginOk?id=id
	로그인 실패 처리 => 경고 메시지 띄우고 사용자가 확인을 클릭하면 로그인 폼으로
--%>

<%
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	
	if(id.equals("admin") && pass.equals("1234")) { // 로그인 성공
		/* 요청한 자원 이쪽으로 이동 - URL 응답 리다이렉트 */
		response.sendRedirect("sendRedirectLoginOk.jsp?id=" + id);
	}
	else { /* 로그인 실패 */
%>
		<script>
			alert("아이디 또는 패스워드가 맞지 않습니다.")
			/* history.back(); */
			location.href="sendRedirectLoginForm.jsp";
		</script>	
<%
	}
%>