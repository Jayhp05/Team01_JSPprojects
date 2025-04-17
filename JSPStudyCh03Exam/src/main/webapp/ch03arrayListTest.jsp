<%@page import="java.util.ArrayList"%>
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
	ArrayList<Student> studentList = new ArrayList<Student>();

	Student student = new Student("홍길동", 23, "남성");
	studentList.add(student);
	
	student = new Student("어머나", 21, "여성");
	studentList.add(student);
	
	student = new Student("왕호감", 22, "남성");
	studentList.add(student);
	
	student = new Student("왕빛나", 23, "여성");
	studentList.add(student);
	
	student = new Student("이나래", 25, "여성");
	studentList.add(student);
	
	request.setAttribute("studentList", studentList);
	pageContext.forward("ch03testResult.jsp");
	
%>