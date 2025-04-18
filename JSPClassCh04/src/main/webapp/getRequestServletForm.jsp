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
	<form name="f1" action="getRequest" >
		첫 번째 숫자 : <input type="number" name="num1" min="1" /><br/>
		두 번째 숫자 : <input type="number" name="num2" min="1" /><br/>
		<input type="reset" value="다시쓰기" />
		<input type="submit" value="전송하기" />
	</form>
</body>
</html>