<%-- request 내장객체를 이용한 클라이언트와 서버정보 출력하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 클라이언트의 요청방식이 GET인지 POST인지 문자열로 반환 한다.
	String method = request.getMethod();
	String methodTitle = "";
	
	// getMethod()의 결과는 대문자로 반환 된다.
	if(method.equals("GET")) {
		methodTitle = "GET 방식 요청정보 보기";
		
	} else {
		methodTitle = "POST 방식 요청정보 보기";
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= methodTitle  %></title>
</head>
<body>
	<h1><%= methodTitle %></h1>
	<h2>서버 정보</h2>
	<ul>		
		<li>서버 이름 : <%= request.getServerName() %></li>
		<li>서버 포트 : <%= request.getServerPort() %></li>		
	</ul>
	
	<h2>요청 정보</h2>
	<ul>
		<li>요청방식 : <%= request.getMethod() %></li>
		<li>프로토콜 :  <%= request.getProtocol() %></li>
		
		<%-- 클라이언트가 요청한 전체 경로를 반환 한다. --%>
		<li>요청URL: <%= request.getRequestURL() %></li>
		
		<%-- 서버 이름과 포트를 제외한 ContextPath를 포함한 경로를 반환 한다. --%>
		<li>요청URI : <%= request.getRequestURI() %></li>
		
		<%-- 웹 어플리케이션의 이름을 ContextPath라 한다. --%>
		<li>컨텍스트경로 : <%= request.getContextPath() %></li>
		<li>컨텐트타입 : <%= request.getContentType() %></li>
			
		<%-- 
			요청 본문에 추가되어 서버로 전송된 요청 데이터의 크기를 반환
			파라미터 이름과 파라미터 값을 포함한 전체 데이터의 크기를 의미한다.
			
			예) name=홍길동&pass=1234 
				name=%ED%99%8D%EA%B8%B8%EB%8F%99&pass=1234
			
			위의 예는 POST 방식으로 요청된 데이터로 데이터가 요청 본문에 추가되어 
			서버로 전송되며 컨텐트의 크기는 42byte 이므로 42를 반환 한다.
			GET 방식의 요청에서는 요청 본문에 데이터가 추가되는 것이 아니라
			URL 끝에 추가되어 파라미터가 전송되므로 -1을 반환 한다.
		 --%>
		<li>컨텐트 길이 : <%= request.getContentLength() %></li>		
	</ul>
	<h2>클라이언트 정보</h2>
	<ol>
		<li>클라이언트 이름 : <%= request.getRemoteHost() %></li>
		<!--		
			이클립스의 서버 실행 환경이 IPv6 형태로 설정되어 있어 현재 사용되는 
			IPv4 형태의 IP 정보를 확인하려면 아래와 같이 설정하고 실행해야 한다.
			 
			먼저 Tomcat 서버가 실행 중이라면 서버를 종료하고
			프로젝트 마우스 오른쪽 -> RunAs -> Run Configurations를
			선택하면 Run Configurations 설정 창이 화면에 나타난다.
			
			좌측의 리스트에서 > Apache Tomcat을 클릭하여 확장하면 현재 이클립스에
			등록된 Tomcat 서버가 나타나는데 이 서버를 선택하면 우측에 여러 탭 메뉴가
			나타나는 이 탭 중에서 Arguments 탭을 선택한 후 VM arguments 설정 정보
			입력란에 기존에 설정된 값들은 변경하지 말고 맨 아래로 이동해 
			아래와 같이 IPv4를 설정하는 명령 한 줄을 추가하자.
			
			-Djava.net.preferIPv4Stack="true"
			
			그리고 "Apply" 버튼을 클릭해 적용하고 "Run" 버튼으로 서버를 실행하면 된다.
		//-->
		<li>클라이언트 주소 : <%= request.getRemoteAddr() %></li>
		<li>클라이언트 포트 : <%= request.getRemotePort() %></li>
		<li>클라이언트 유저 : <%= request.getRemoteUser() %></li>
		<li>웹 브라우저와 클라이언트의 시스템 정보 보기  :<br/>
			 <%= request.getHeader("User-Agent") %></li>
		<li>브라우저 지원 FileType : <%= request.getHeader("Accept") %></li>
		<li>바로이전 URL : <%= request.getHeader("referer") %></li>	
	</ol>
</body>
</html>