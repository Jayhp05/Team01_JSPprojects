<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>POST 방식 요청처리</title>
</head>
<body>
	<!-- 
		form 태그의 method 속성을 post로 지정 하였다. default는 get 이다.
		브라우저는 현재 문서의 문자 셋을 기준으로 폼 데이터를 인코딩하여 서버로 전송한다. 
	-->
	<form name="f1" action="postRequest" method="post">
		이름 : <input type="text" name="name" /><br/>
		주소 : <input type="text" name="address" /><br/>		
		<input type="submit" value="주소입력" />
	</form>
	<!-- 
		a 태그를 이용한 요청은 GET 방식이므로 요청 데이터를 
		서버로 보낼 때는 요청 URL 뒤에 데이터를 추가해 서버로 보낸다. 
	-->
	<a href="postRequest?name=홍길동&address=서울 관악구 봉천동">링크로 요청하기</a>
</body>
</html>