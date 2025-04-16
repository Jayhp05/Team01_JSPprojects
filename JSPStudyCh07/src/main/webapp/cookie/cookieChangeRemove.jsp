<%-- 쿠키를 변경하고 삭제하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.*"%>
<%	
	// request 내장 객체로부터 모든 쿠키를 배열로 얻어온다.
	Cookie[] cookies = request.getCookies();
	
	// 쿠키 존재하면 반복문 안에서 쿠키에 접근한다.
	if(cookies != null) {
		
		for(Cookie c : cookies) {
				
			String name = c.getName();
			
			// id라는 쿠키 이름이 존재하면 쿠키의 값을 변경한다.
			if(name.equals("id")) {
				
				/* 기존에 존재하는 쿠키 이름으로 새로운 쿠키 값을 지정하여 쿠키를
				 * 생성하고 쿠키의 유효기간을 5분으로 설정한 후 response 내장 객체에
				 * 새로 생성한 쿠키를 추가한다. 만약 이미 기존에 존재하는 쿠키 이름으로
				 * 쿠키를 추가하게 되면 같은 이름의 쿠키가 추가로 만들어 진다.
				 **/  
				Cookie cookie = new Cookie(name, "cookie");
				cookie.setMaxAge(60*5);
				
				/* 브라우저는 웹 서버로 요청을 보낼 때 기본적으로 그 웹 서버(도메인)에
				 * 속하는 모든 쿠키를 함께 보내는데 아래와 같이 setPath()를 통해서 새로
				 * 생성한 쿠키에 경로를 지정하면 이 경로로 요청할 때만 쿠키를 전송한다. 
				 * 다시 말해 새로 생성한 쿠키에 아래와 같이 경로를 지정하면 웹 브라우저는 
				 * http://localhost:8080/JSPStudyCh07/cookie/ 로 요청을 할 때만
				 * id라는 이름을 가진 쿠키를 웹 서버로 같이 전송한다.
				 **/
				cookie.setPath("/JSPStudyCh07/cookie/");
				response.addCookie(cookie);
			
			// name이라는 쿠키 이름이 존재하면 쿠키를 삭제한다.	
			} else if(name.equals("name")) {				
				
				/* 쿠키의 유효기간을 설정하는 setMaxAge() 메서드를 호출하면서
				 * 시간을 0으로 지정하면 쿠키의 유효기간이 0초가 되므로 이런 쿠키를
				 * 받은 웹 브라우저는 같은 이름의 쿠키를 사용자 컴퓨터에서 삭제한다.
				 **/
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키 변경하고 삭제하기</title>
</head>
<body>
쿠키 변경과 삭제가 완료됨<br/>
<a href="cookieView.jsp">/JSPStudyCh07/cookie/로 쿠키 확인하기</a><br/><br/>
<a href="../cookieinfo/cookieView.jsp" >/JSPStudyCh07/cookieInfo/로 쿠키 확인하기</a>
</body>
</html>