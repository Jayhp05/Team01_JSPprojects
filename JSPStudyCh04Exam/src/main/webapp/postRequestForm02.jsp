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
	<h2>회원 가입</h2>
	<form name="f1" action="postRequest" method="post">
		이름 : <input type="text" name="name" /><br/>
		나이 : <input type="text" name="age" /><br/>
		성별 : <input type="radio" name="gender" value="남성"/>남성&nbsp;
 				<input type="radio" name="gender" value="여성"/>여성<br/>
		주소 : <input type="text" name="address" /><br/>
		<input type="reset" value="다시쓰기" />
		<input type="submit" value="전송하기" />
	</form>
</body>
</html>