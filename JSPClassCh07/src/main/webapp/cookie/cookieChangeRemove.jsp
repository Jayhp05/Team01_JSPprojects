<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Cookie[] cookies = request.getCookies();

	if(cookies != null) {
		for(Cookie c : cookies) {
			String name = c.getName();
			
			if(name.equals("id")) {
				Cookie cookie = new Cookie(name, "cookie");
				cookie.setMaxAge(60 * 3);
				cookie.setPath("/JSPClassCh07/cookie/");
				response.addCookie(cookie);
			}
			else if(name.equals("name")) {
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
<title>Insert title here</title>
</head>
<body>
	<a href="cookieView.jsp">JSPClassCh07/cookie/로 쿠키 확인하기.</a><br><br>
	<a href="../cookieinfo/cookieView.jsp" >/JSPStudyCh07/cookieInfo/로 쿠키 확인하기</a>
</body>
</html>