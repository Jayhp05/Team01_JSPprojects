<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.jspstudy.ch08.Member"%>
<%
	// 세션 영역의 속성에 저장된 Member 객체 얻기
	Member member = (Member) session.getAttribute("member");	

	request.setCharacterEncoding("utf-8");
	
	String gender = request.getParameter("gender");
	int age = Integer.valueOf(request.getParameter("age"));
	String interest = request.getParameter("interest");	
	boolean noticeMail = Boolean.valueOf(request.getParameter("noticeMail"));
	boolean addMail = Boolean.valueOf(request.getParameter("addMail"));
	boolean infoMail = Boolean.valueOf(request.getParameter("infoMail"));
	String job = request.getParameter("job");
	
	// 회원 추가정보 폼으로부터 전송된 데이터를 Member 객체의 필드에 설정 
	member.setGender(gender == null ? null : gender.equals("male") ? "남자" : "여자");
	member.setAge(age);
	member.setInterest(interest);
	member.setNoticeMail(noticeMail);
	member.setAddMail(addMail);
	member.setInfoMail(infoMail);
	member.setJob(job);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table {
		border: 1px solid blue;
		border-collapse: collapse; 
	}
	td { border: 1px solid blue; height: 30px; }
	.title {
		width: 100px;
		padding-left: 5px;
	}
	.content {
		width: 250px;
		padding-left: 5px;
	}
</style>
</head>
<body>	
	<table>
		<tr><td colspan="2" style="text-align: center; 
				height: 30px; line-height: 30px">
				<h3>회원 등록 완료</h3>
				아래와 같이 회원 등록이 완료 되었습니다.<br/></td></tr>
		<tr><td  class="title">이 름</td>
			<td class="content">${ sessionScope.member.name }</td></tr>
		<tr><td class="title">아이디</td>
			<td class="content">${ sessionScope.member.id }</td></tr>
		<tr><td class="title">비밀번호</td>
			<td class="content">${ sessionScope.member.pass }</td></tr>
		<tr><td class="title">전화번호</td>
			<td class="content">${ sessionScope.member.phone }</td></tr>
		<tr><td class="title">성 별</td>
			<td class="content">
				${ sessionScope.member.gender == null 
							? "입력않됨" : sessionScope.member.gender }
			</td></tr>
		<tr><td class="title">나 이</td>
			<td class="content">${ sessionScope.member.age }</td></tr>
		<tr><td class="title">관심분야</td>
			<td class="content">${ sessionScope.member.interest }</td></tr>
		<tr><td rowspan="3" class="title">메일수신</td>
			<td class="content">
				${sessionScope.member.noticeMail ? "공지메일 받음" : "공지메일 받지 않음"}
			</td></tr>
		<tr><td class="content">
			${ sessionScope.member.addMail ? "광고메일 받음" : "광고메일 받지 않음" }
			</td></tr>
		<tr><td class="content">
			${ sessionScope.member.infoMail ? "정보메일 받음" : "정보메일 받지 않음" }
		</td></tr>
		<tr><td class="title">직 업</td>
			<td class="content">${ sessionScope.member.job }</td></tr>
	</table>	 
</body>
</html>
