<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 기본 정보 입력 폼</title>
</head>
<body>
	<h2>학생 등록 정보</h2>	
	<!-- form 태그의 method 속성을 지정하지 않으면 GET 방식 요청이 된다. -->
	<form action="formData02" method="get">
		<p>학&nbsp;&nbsp;생&nbsp;&nbsp;명 : <input type="text" name="name" /></p>
		<p>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;별 :
			<input type="radio" name="gender" value="남자" /> 남
			<input type="radio" name="gender" value="여자" /> 여
		</p>
		<p>연&nbsp;&nbsp;락&nbsp;&nbsp;처 :
			<select name="call">
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="018">018</option>
			</select> -
			<input type="text" name="call1" size="4" maxlength="4" /> -
			<input type="text" name="call2" size="4" maxlength="4" />
		</p>
		<p>희망 취업 분야 : <br>
			<input type="checkbox" name="hopeJob" value="SI" /> SI업체
			<input type="checkbox" name="hopeJob" value="SM" /> SM업체
			<input type="checkbox" name="hopeJob" value="솔루션" /> 솔루션 업체 
		</p>
		<p>관심분야 :
			<select name="star[]" multiple style="vertical-align: top;">
				<option>안드로이드</option>
				<option>자바스크립트</option>
				<option>스프링</option>
				<option>제이쿼리</option>
			</select>
		</p>
		<p>
			<input type="reset" value="다시쓰기" />
			<input type="submit" value="등록하기" />
		</p>
	</form>
</body>
</html>