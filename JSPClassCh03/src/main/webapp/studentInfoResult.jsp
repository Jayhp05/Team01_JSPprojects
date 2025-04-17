<%@page import="com.jspstudy.ch03.vo.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 
	화면을 만드려고 이 파일로 이동
 -->
 
<%
	Student s = (Student) request.getAttribute("student");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>학생 정보 출력 - 표현식(Expression)</h2>
	이름 : <%= s.getName() %><br>
	나이 : <%= s.getAge() %><br>
	성별 : <%= s.getGender() %><br>
	
	<h2>학생 정보 출력 - 표현 언어(Expression Language)</h2>
	이름 : ${student.name}<br> <!-- getter메서드를 알아서 가져옴 -->
	나이 : ${student.age}<br>
	성별 : ${student.gender}<br>

</body>
</html>