<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.jspstudy.ch03.vo.Student" %>
    
<!-- 
	요청(request)
	요청을 받아서 처리하는 역할을 담당 jsp
	Controller - Service
	
	응답을 위해서 HTML  형식의 문서 - HTTP - 응답(Response)
 -->
 
<%
	/* 학생의 정보 or 학생 리스트 */
	Student s1 = new Student("이순신", 23, "남성");
	Student s2 = new Student();
	s2.setName("홍길동");
	s2.setAge(25);
	s2.setGender("남성");

	// 요청을 처리한 결과 데이처 -> 모델(Model) -> 모델 클래스, 서비스 클래스
	// 비즈니스 로직 계층, 서비스 계층
	//저장할 수 있는 공간 - 내장객체의 속성역역 - 4개 객체
	//pageContext, request, session, application
 	request.setAttribute("student", s1);

 	/* 다음 모듈을 호출 - 이동 - 제어를 이동 */
	// 처리를 완료하고 뷰를 만들기 위해서 제어를 이동
	pageContext.forward("studentInfoResult.jsp");
%>

<!-- <!DOCTYPE html> ################### 응답을 위한 jsp파일이라 html 형식 불필요
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html> -->