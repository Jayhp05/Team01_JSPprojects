<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>   
<!--
	이 JSP 페이지를 webapp/cookie/ 폴더에 작성하고
	이 파일을 	webapp/cookieinfo/ 폴더에 복사한 후 테스트 할 것
	
	cookieChangeRemove.jsp에서 쿠키 이름이 id인 쿠키를 변경할 때
	cookie.setPath("/JSPStudyCh07/cookie/");를 지정했기 때문에
	/JSPStudyCh07/cookie/로 들어오는 요청은 브라우저가 쿠키를 같이
	전송하지만 /JSPStudyCh07/cookieinfo/로 들어오는 요청은 경로가
	다르기 때문에 브라우저가 쿠키를 같이 전송하지 않는다. 또한 name인
	쿠키는 c.setMaxAge(0);로 설정하였기 때문에 브라우저가 응답을
	받으면 해당 쿠키를 삭제하기 때문에 다음 요청부터 쿠키를 전송하지 않는다.
--> 
<%
	// request 내장 객체로부터 모든 쿠키를 배열로 얻어온다.
	Cookie[] cookies = request.getCookies();

// 쿠키가 존재하면 쿠키를 읽어와 출력한다.
if(cookies != null) {
	
	for(Cookie c : cookies) {
		out.println(c.getName() + ", " + c.getValue() + "<br/>");
	}
}
%>