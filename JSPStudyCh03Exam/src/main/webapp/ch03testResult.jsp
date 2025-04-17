<%@page import="java.util.ArrayList"%>
<%@page import="com.jspstudy.ch03.vo.Student"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 
	화면을 만드려고 이 파일로 이동
 -->
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h2>스크립틀릿과 표현식을 이용한 학생 리스트 출력</h2>
	<ul>	<!-- getter메서드를 알아서 가져옴 -->
		<%
			ArrayList<Student> s1 = (ArrayList<Student>) request.getAttribute("studentList");
		
			for(int i = 0; i < s1.size(); i++) {
				Student s = s1.get(i);
		%> 
				<li>
					<%= s.getName() %>(<%= s.getAge() %>) - <%= s.getGender() %>
				</li>
		<%
			}
		%>
	</ul>
	
	<h2>JSTL과 EL을 이용한 학생 리스트 출력</h2>
	<ul>
		<!-- li 태그만 반복해서 출력 -->
		<c:forEach var="student" items="${ studentList }" >
			<li>
				${student.name}(${student.age}) - ${student.gender}
			</li>
		</c:forEach>
	</ul>

</body>
</html>