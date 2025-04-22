<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GET 방식 요청처리</title>
</head>
<body>
	<!-- 
		form 태그의 method 속성을 생략했기 때문에 default인 get 방식이 적용된다.
		브라우저는 현재 문서의 문자 셋을 기준으로 폼 데이터를 인코딩하여 서버로 전송한다.
	-->	
	<h1>숫자1과 숫자2 사이의 합 구하기</h1>
	<form name="f1" action="getRequest" >
		숫자 1 : <input type="number" name="num1" min="1" /><br/>
		숫자 2 : <input type="number" name="num2" min="1" /><br/>
		<input type="submit" value="계산결과보기" />
	</form>
</body>
</html>	