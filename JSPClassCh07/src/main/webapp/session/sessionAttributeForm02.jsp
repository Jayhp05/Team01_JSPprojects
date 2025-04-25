<%@page import="com.jspstudy.ch08.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	String phone1 = request.getParameter("phone1");
	String phone2 = request.getParameter("phone2");
	String phone3 = request.getParameter("phone3");
	String phone = phone1 + "-" + phone2 + "-" + phone3;
	
	// 회원 기본정보 폼으로부터 전송된 데이터를 Member 객체의 필드에 설정- 135 
	Member member = new Member();
	
	member.setName(name);
	member.setId(id);
	member.setPass(pass);
	member.setPhone(phone);
	
	session.setAttribute("member", member);
 %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="font-size: 12px;">
	<h2>회원 추가 정보 입력</h2>
	<form action="sessionAttributeProcess.jsp" method="post">		
		<p>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;별 : 
			<input type="radio" name="gender" value="male"/>남&nbsp;&nbsp;&nbsp;
			<input type="radio" name="gender" value="female"/>여</p>
		<p>나&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이 : 
			<input type="text" name="age" size="3"/></p>
		<p>관심분야 : 
			<select name="interest">
				<option value="Android">안드로이드</option>
				<option value="JavaScript">자바스크립트</option>
				<option value="Spring" selected>스프링</option>
				<option value="jQuery">제이쿼리</option>
				<option value="Oracle">오라클</option>
			</select></p>	
		<p>메일수신 : <br/>
			<input type="checkbox" name="noticeMail" value="true"/>
				공지메일 받음&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="addMail" value="true" />
				광고메일 받음&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="infoMail" value="true" />
				정보메일 받음&nbsp;&nbsp;&nbsp;</p>
		<p>직&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업 :	
			<select name="job">
				<option>회사원</option>
				<option>취업준비생</option>
				<option>학생</option>
				<option>주부</option>				
				<option>기타</option>
			</select></p>
		<p><input type="reset" value="다시쓰기" />
		<input type="submit" value="가입하기" /></p>
	</form>
</body>
</html>
