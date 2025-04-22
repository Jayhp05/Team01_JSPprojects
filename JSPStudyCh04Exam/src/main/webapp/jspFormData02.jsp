<%@ page language="java" contentType="text/html; charset=UTF-8"
    pagedffvceEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 기본 정보 입력 폼</title>
</head>
<body>
	<h2>학생 등록 정보</h2>	
	<!-- form 태그의 method 속성을 지정하지 않으면 GET 방식 요청이 된다. -->
	<form name="fMember1" action="test.do" method="get">
		<p>학&nbsp;&nbsp;생&nbsp;&nbsp;명 : 
			<input type="text" name="name" /></p>
		<p>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;별 :
			<input type="radio" name="gender" value="male"/>남&nbsp;&nbsp;&nbsp;
			<input type="radio" name="gender" value="female"/>여</p>
		<p>연&nbsp;&nbsp;락&nbsp;&nbsp;처 : 		
			<select name="call">
				<option>010</option>
				<option>011</option>
				<option>018</option>
				<option>019</option>
			</select> -
            <input type="text" name="cell" title="전화번호"> -
            <input type="text" name="cell">
		<!-- 
			체크박스는 name 속성의 값을  각각 지정할 수도 있고
			동일하게 지정할 수도 있다.
		-->
		<p>희망 취업 분야 :
			<input type="checkbox" name="si"/>
				SI 업체&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="sm"/>
				SM 업체&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="solution"/>
				솔루션 업체&nbsp;&nbsp;&nbsp;</p>		
		<!-- 
			<option> 태그에 value 속성을 지정하지 않으면 
			<option> 태그로 감싼 문자열이 value가 된다.
		-->
		<p>관심분야 :	
			<select name="star">
				<option>안드로이드</option>
				<option>자바스크립트</option>
				<option>스프링</option>
				<option>제이쿼리</option>
			</select></p>			
		<!-- 
			submit 버튼이 클릭되면 form 태그의 action 속성에서 
			지정한 URL로 무조건 폼 데이터를 전송한다.
		-->
		<p><input type="reset" value="다시쓰기" />
		<input type="submit" value="가입하기" /></p>
	</form>
</body>
</html>