<%-- 쿠키(Cookie) 생성하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	쿠키 생성 테스트 용 페이지 - 교안에 없음
	같은 JSP 페이지에서 이름이 같은 쿠키를 추가하는 경우에
	Edge와 Chrome에서 쿠키가 수정되지만 다른 JSP 페이지에서
	같은 쿠키 이름으로 추가하면 같은 이름의 쿠키가 하나 더 추가된다.  
 --%>    
<%
	Cookie[] cookies = request.getCookies();
	if(cookies == null) {
		Cookie cookie = new Cookie("id", "midas");
		
		/* 쿠키의 유효기간을 3분으로 설정하고 있다.
		 * 쿠키의 유효기간을 지정하지 않으면 브라우저가 실행되는 동안 유효하다.
		 **/
		cookie.setMaxAge(60 * 3);
		
		/* 응답 데이터의 정보를 저장하고 있는 response 객체에 쿠키를 추가한다.
		 * 아래와 같이 응답 데이터에 쿠키를 추가하면 웹 브라우저로 쿠키가 전달되고 웹 브라우저는
		 * 이 쿠키를 받아서 사용자 컴퓨터의 메모리나 하드 디스크에 쿠키를 저장하게 된다. 
		 * 쿠키는 하나의 도메인당 20개, 쿠키 1개당 4KB 총 300개를 저장할 수 있다. 
		 **/
		response.addCookie(cookie);
		response.addCookie(new Cookie("name", "John"));
		System.out.println("cookies is null");
%>
	쿠키 이름 : <%= cookie.getName() %><br/>
	쿠키 값 : <%= cookie.getValue() %><br/>
	쿠키 유효기간 : <%= cookie.getMaxAge() %>초<br/>
<%
	} else {
		
		for(Cookie c : cookies) {
			
			String name = c.getName();
			
			// id라는 쿠키 이름이 존재하면 쿠키의 값을 변경한다.
			if(name.equals("id")) {
				
				/* 기존에 존재하는 쿠키 이름으로 새로운 쿠키 값을 지정하여 쿠키를
				 * 생성하고 쿠키의 유효기간을 5분으로 설정한 후 response
				 * 내장 객체에 새로 생성한 쿠키를 추가한다. 만약 이미 기존에 존재하는
				 * 쿠키 이름으로 쿠키를 추가하게 되면 기존의 쿠키를 덮어쓰게 된다.
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
				cookie.setPath("/JSPStudyCh08/cookie/");
				response.addCookie(cookie);
%>			
		쿠키 이름 : <%= cookie.getName() %><br/>
		쿠키 값 : <%= cookie.getValue() %><br/>
		쿠키 유효기간 : <%= cookie.getMaxAge() %>초<br/>
<%		
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
		System.out.println("cookies is not null");
	} 
%>