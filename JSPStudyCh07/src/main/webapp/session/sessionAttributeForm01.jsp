<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="font-size: 12px;">
	<h2>회원 기본 정보 입력</h2>
	<form action="sessionAttributeForm02.jsp" method="post">
		<p>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름 : 
			<input type="text" name="name" /></p>
		<p>아&nbsp;&nbsp;이&nbsp;&nbsp;디 : 
			<input type="text" name="id" /></p>
		<p>비밀번호 : <input type="password" name="pass" /></p>
		<p>연&nbsp;&nbsp;락&nbsp;&nbsp;처 : 	
			<select name="phone1">
				<option>010</option>
				<option>011</option>
				<option>016</option>
				<option>017</option>
				<option>018</option>
				<option>019</option>
			</select>-<input type="text" name="phone2" size="2" maxlength="4"/>
			-<input type="text" name="phone3" size="2" /></p>	
		<p><input type="reset" value="다시쓰기" />
		<input type="submit" value="다음단계" /></p>
	</form>
</body>
</html>